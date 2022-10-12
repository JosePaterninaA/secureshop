package com.secureshop.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtFilter extends AbstractGatewayFilterFactory {

    @Autowired
    AuthenticationFilter authenticationFilter;

    @Override
    public GatewayFilter apply(Object config) {
        return authenticationFilter;
    }
}
