package org.restjwtdemo.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import feign.RequestInterceptor;

@Configuration
@Import({ JpaConfig.class, ApplicationConfig.class })
@EnableFeignClients(basePackages = { "org.restjwtdemo.service.user","org.restjwtdemo.service.login" })
public class IntegrationTestConfig {
    @Bean
    public String url() {
        return "http://localhost:8082";
    }
    @Bean
    public RequestInterceptor jwtRequestInterceptor() {
        return new JwtRequestInterceptor();
    }
    @Bean
    public ClientHttpRequestInterceptor restRequestInterceptor() {
        return new RestRequestInterceptor();
    }
}
