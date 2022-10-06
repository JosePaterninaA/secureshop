package com.secutitypoc.jwtauthserver.models.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class JwtResponse {
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
