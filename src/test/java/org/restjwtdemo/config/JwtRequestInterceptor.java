package org.restjwtdemo.config;

import org.restjwtdemo.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class JwtRequestInterceptor implements RequestInterceptor {
    // TODO constructor inject the token
//    @Value("${jwt}")
    @Value("jwt")
    private String jwtToken;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " + JwtAuthenticationFilter.createToken("admin1"));
//        requestTemplate.header("Authorization: Bearer " + this.jwtToken);
    }
}