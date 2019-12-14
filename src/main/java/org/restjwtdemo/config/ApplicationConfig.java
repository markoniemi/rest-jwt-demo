package org.restjwtdemo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "org.restjwtdemo")
@EntityScan(basePackages = "org.restjwtdemo.model")
@Import({ JpaConfig.class, WebServiceConfig.class, WebSecurityConfig.class, RestConfig.class })
public class ApplicationConfig {
}