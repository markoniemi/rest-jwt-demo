package org.survey.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ JpaConfig.class,   WebServiceConfig.class,
        WebSecurityConfig.class })
public class ApplicationConfig {
}