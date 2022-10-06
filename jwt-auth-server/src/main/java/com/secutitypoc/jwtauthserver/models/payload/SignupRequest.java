package com.secutitypoc.jwtauthserver.models.payload;

import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest{
    String username;
    String password;
    String email;
    Set<String> roles;
}
