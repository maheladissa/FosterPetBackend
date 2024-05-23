package com.fosterpet.backend.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<?> register(
        @RequestBody RegisterRequest request)
        {
            try {
                return ResponseEntity.ok(authenticationService.register(request));
            }
            catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request)
        {
            try {
                return ResponseEntity.ok(authenticationService.authenticate(request));
            }
            catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());

            }
        }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(
            @RequestBody VerificationRequest request)
        {
            try {
                return ResponseEntity.ok(authenticationService.verify(request));
            }
            catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request)
        {
            try {
                return ResponseEntity.ok(authenticationService.resetPassword(request));
            }
            catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    @PostMapping("/send-reset-password-code")
    public ResponseEntity<?> sendResetPasswordCode(
            @RequestBody ForgotPasswordRequest request)
        {
            try {
                return ResponseEntity.ok(authenticationService.sendResetPasswordCode(request));
            }
            catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
}
