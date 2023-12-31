package com.fosterpet.backend.authentication;

import com.fosterpet.backend.config.JwtService;
import com.fosterpet.backend.user.EmailVerificationService;
import com.fosterpet.backend.user.Role;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailVerificationService emailVerificationService;

    public AuthenticationResponse register(RegisterRequest request) {
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
                    .build();
            if(Objects.equals(emailVerificationService.sendVerificationCode(request.getEmail()), "SUCCESSFULLY_COMPLETED")){
                user.setIsEmailVerified(false);
                userRepository.save(user);
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

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if (!user.getIsEmailVerified()){
            return AuthenticationResponse.builder()
                    .status("Email not verified")
                    .build();
        }
        else {
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
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

}
