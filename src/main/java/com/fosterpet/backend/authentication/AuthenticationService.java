package com.fosterpet.backend.authentication;

import com.fosterpet.backend.common.AzureIdentityGenerator;
import com.fosterpet.backend.config.JwtService;
import com.fosterpet.backend.payment.PaymentService;
import com.fosterpet.backend.session.SessionService;
import com.fosterpet.backend.user.EmailVerificationService;
import com.fosterpet.backend.user.Role;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.user.UserRepository;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailVerificationService emailVerificationService;
    private final AzureIdentityGenerator azureIdentityGenerator;
    private final PaymentService paymentService;
    private final SessionService sessionService;

    public AuthenticationResponse register(RegisterRequest request) throws StripeException {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            return AuthenticationResponse.builder()
                    .status("Email already exist")
                    .build();
        }
        else {
            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .azureCommunicationId(azureIdentityGenerator.createUser().getId())
                    .isAccountActive(true)
                    .createdAt(Instant.now())
                    .build();
            if(Objects.equals(emailVerificationService.sendVerificationCode(request.getEmail()), "SUCCESSFULLY_COMPLETED")){
                user.setIsEmailVerified(false);
                User saved = userRepository.save(user);
                saved.setStripeCustomerId(paymentService.createStripeCustomer(request.getEmail(), request.getFirstName() + " " + request.getLastName(), saved.getUserId()));
                userRepository.save(saved);
                return AuthenticationResponse.builder()
                        .status("Success")
                        .build();
            }
            else{
                return AuthenticationResponse.builder()
                        .status("Email Sending Failed")
                        .build();
            }
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, String deviceType, String os, String expoDeviceToken) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if (!user.getIsEmailVerified()){
            return AuthenticationResponse.builder()
                    .status("Email not verified")
                    .build();
        }
        else {
            var jwtToken = jwtService.generateToken(user);
            sessionService.createSession(
                    user.getUserId(),
                    jwtToken,
                    jwtService.extractExpiration(jwtToken).toInstant(),
                    deviceType,
                    os,
                    expoDeviceToken
            );
            return AuthenticationResponse.builder()
                    .userId(user.getUserId())
                    .token(jwtToken)
                    .status("Success")
                    .build();
        }
    }

    public AuthenticationResponse verify(VerificationRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if(user.getIsEmailVerified()){
            return AuthenticationResponse.builder()
                    .status("Email already verified")
                    .build();
        }
        else{
            if(emailVerificationService.verifyCode(request.getEmail(), request.getVerificationCode())){
                user.setIsEmailVerified(true);
                userRepository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .userId(user.getUserId())
                        .token(jwtToken)
                        .status("Success")
                        .build();
            }
            else{
                return AuthenticationResponse.builder()
                        .status("Email Verification Failed")
                        .build();
            }
        }
    }

    public AuthenticationResponse sendResetPasswordCode(ForgotPasswordRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if (!user.getIsEmailVerified()){
            return AuthenticationResponse.builder()
                    .status("Email not verified")
                    .build();
        }
        else {
            if(Objects.equals(emailVerificationService.sendVerificationCode(request.getEmail()), "SUCCESSFULLY_COMPLETED")){
                return AuthenticationResponse.builder()
                        .status("Success")
                        .build();
            }
            else{
                return AuthenticationResponse.builder()
                        .status("Email Sending Failed")
                        .build();
            }
        }
    }

    public AuthenticationResponse resetPassword(ResetPasswordRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if (!user.getIsEmailVerified()){
            return AuthenticationResponse.builder()
                    .status("Email not verified")
                    .build();
        }
        else {
            if(emailVerificationService.verifyCode(request.getEmail(), request.getVerificationCode())){
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                userRepository.save(user);
                return AuthenticationResponse.builder()
                        .status("Success")
                        .build();
            }
            else{
                return AuthenticationResponse.builder()
                        .status("Email Verification Failed")
                        .build();
            }
        }
    }

    public AuthenticationResponse adminAuthenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if (!user.getIsEmailVerified()){
            return AuthenticationResponse.builder()
                    .status("Email not verified")
                    .build();
        }
        else if (user.getRole() != Role.ADMIN){
            return AuthenticationResponse.builder()
                    .status("Not an admin")
                    .build();
        }
        else {
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .userId(user.getUserId())
                    .token(jwtToken)
                    .status("Success")
                    .build();
        }
    }

}
