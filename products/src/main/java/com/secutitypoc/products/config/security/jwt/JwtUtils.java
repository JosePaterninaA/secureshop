package com.secutitypoc.products.config.security.jwt;

import com.secutitypoc.products.config.security.AuthUser;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class JwtUtils {
    @Value("${auth-server.jwtSecret}")
    private String jwtSecret;

    public String getUserNameFromJwtToken(String token) {
        try{
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        } catch (MalformedJwtException e) {
            //logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            //logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            //logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            //logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return null;
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            //logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            //logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            //logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            //logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public AuthUser parseUser(String token){
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();

            List<Map> grantedAuthorities = (List) body.get("roles");

            List<SimpleGrantedAuthority> authorities =
                    grantedAuthorities
                    .stream()
                    .map(authorityMap -> new SimpleGrantedAuthority((String) authorityMap.get("authority")))
                    .toList();

            String username = body.getSubject();
            Long id = Long.valueOf((Integer) body.get("id"));

            System.out.println("exp-> "+ body.getExpiration().toString());

            return AuthUser
                    .builder()
                    .username(username)
                    .id(id)
                    .authorities(authorities)
                    .build();

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }


}
