package com.secutitypoc.jwtauthserver.models.payload;

import lombok.Data;

@Data
public class LoginRequest{
    String username;
    String password;
}
