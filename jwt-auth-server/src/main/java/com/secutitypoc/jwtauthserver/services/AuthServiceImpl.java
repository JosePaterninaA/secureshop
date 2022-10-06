package com.secutitypoc.jwtauthserver.services;

import com.secutitypoc.jwtauthserver.config.security.jwt.JwtUtils;
import com.secutitypoc.jwtauthserver.config.security.services.AuthUser;
import com.secutitypoc.jwtauthserver.exceptions.RegistrationException;
import com.secutitypoc.jwtauthserver.models.Role;
import com.secutitypoc.jwtauthserver.models.RoleName;
import com.secutitypoc.jwtauthserver.models.User;
import com.secutitypoc.jwtauthserver.models.payload.JwtResponse;
import com.secutitypoc.jwtauthserver.models.payload.LoginRequest;
import com.secutitypoc.jwtauthserver.models.payload.SignupRequest;
import com.secutitypoc.jwtauthserver.repositories.RoleRepository;
import com.secutitypoc.jwtauthserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;



    @Override
    public JwtResponse authenticateUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthUser userDetails = (AuthUser) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String jwt = jwtUtils.generateJwtToken(authentication);
        return JwtResponse
                .builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .token(jwt)
                .build();
    }

    @Override
    public JwtResponse registerUser(SignupRequest request) throws RegistrationException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RegistrationException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("Email is already in use");
        }

        Set<Role> roles = getRoles(request.getRoles());

        User user = User
                .builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .roles(roles)
                .build();
        User savedUser = userRepository.save(user);
        return JwtResponse
                .builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .roles(savedUser.getRoles().stream().map(role -> role.getName().name()).toList())
                .build();
    }

    private Set<Role> getRoles(Set<String> strRoles) throws RegistrationException {
        Set<Role> roles = new HashSet<>();
        try{
            if (strRoles == null) {
                Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    if ("admin".equals(role)) {
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    } else {
                        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                });
            }
        }catch (RuntimeException e){
            throw new RegistrationException(e.getMessage());
        }
        return  roles;
    }
}
