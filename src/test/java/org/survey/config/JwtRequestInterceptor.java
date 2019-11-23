package org.survey.config;

import org.springframework.beans.factory.annotation.Value;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class JwtRequestInterceptor implements RequestInterceptor {
//    @Value("${jwt}")
    @Value("jwt")
    private String jwtToken;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " + this.jwtToken);
//        requestTemplate.header("Authorization: Bearer " + this.jwtToken);
    }
}