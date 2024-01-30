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
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request)
        {
        return ResponseEntity.ok(authenticationService.register(request));
        }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request)
        {
        return ResponseEntity.ok(authenticationService.authenticate(request));
        }

    @PostMapping("/verify")
    public ResponseEntity<AuthenticationResponse> verify(
            @RequestBody VerificationRequest request)
        {
        return ResponseEntity.ok(authenticationService.verify(request));
        }
}
