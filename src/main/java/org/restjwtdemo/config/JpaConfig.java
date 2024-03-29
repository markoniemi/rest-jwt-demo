package org.restjwtdemo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.restjwtdemo")
@EntityScan(basePackages = "org.restjwtdemo.model")
public class JpaConfig {
}
