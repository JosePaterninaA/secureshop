package com.secutitypoc.jwtauthserver.services;

import com.secutitypoc.jwtauthserver.exceptions.RegistrationException;
import com.secutitypoc.jwtauthserver.models.payload.JwtResponse;
import com.secutitypoc.jwtauthserver.models.payload.LoginRequest;
import com.secutitypoc.jwtauthserver.models.payload.SignupRequest;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest request);
    JwtResponse registerUser(SignupRequest request) throws RegistrationException;

}
