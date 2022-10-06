package com.secutitypoc.jwtauthserver.controllers;

import com.secutitypoc.jwtauthserver.exceptions.RegistrationException;
import com.secutitypoc.jwtauthserver.models.payload.JwtResponse;
import com.secutitypoc.jwtauthserver.models.payload.LoginRequest;
import com.secutitypoc.jwtauthserver.models.payload.SignupRequest;
import com.secutitypoc.jwtauthserver.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse response = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            JwtResponse response = authService.registerUser(signUpRequest);
            return ResponseEntity.ok(response);
        } catch (RegistrationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
