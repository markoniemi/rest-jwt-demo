package org.restjwtdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ JpaConfig.class, WebServiceConfig.class, WebSecurityConfig.class, RestConfig.class })
public class ApplicationConfig {
}