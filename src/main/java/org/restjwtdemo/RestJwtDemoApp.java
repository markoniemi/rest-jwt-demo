package org.restjwtdemo;

import org.restjwtdemo.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RestJwtDemoApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
}
