package org.restjwtdemo.config;

import javax.annotation.Resource;

import org.restjwtdemo.model.user.Role;
import org.restjwtdemo.model.user.User;
import org.restjwtdemo.repository.user.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class DatabaseInitBean implements InitializingBean {
    @Resource
    private UserRepository userRepository;
    @Value("${initial.username:admin1}")
    private String username;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("Creating user {}", username);
        userRepository.save(new User(username, "admin", "email", Role.ROLE_ADMIN));
        for (int i = 0; i < 20; i++) {
            userRepository.save(new User("user" + i, "user", "email", Role.ROLE_USER));
        }
    }
}
