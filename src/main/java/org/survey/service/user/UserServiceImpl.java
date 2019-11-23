package org.survey.service.user;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;
import org.survey.model.user.User;
import org.survey.repository.user.UserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component(value = "userService")
@WebService(endpointInterface = "org.survey.service.user.UserService", serviceName = "UserService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Override
    public User[] findAll() {
        log.trace("findAll");
        return IterableUtils.toList(userRepository.findAll()).toArray(new User[0]);
    }

    @Override
    public User create(User user) {
        Validate.notNull(user, "invalid.user");
        Validate.notBlank(user.getUsername(), "invalid.user.username");
        Validate.isTrue(userRepository.findByUsername(user.getUsername()) == null, "exist.user.username");
        log.trace("create: {}", user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User databaseUser = userRepository.findByUsername(user.getUsername());
        Validate.notNull(databaseUser, "User does not exist.");
        databaseUser.setEmail(user.getEmail());
        databaseUser.setPassword(user.getPassword());
        databaseUser.setRole(user.getRole());
        databaseUser.setUsername(user.getUsername());
        log.trace("update: {}", databaseUser);
        return userRepository.save(databaseUser);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        log.trace("findByEmail: {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.trace("delete: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public long count() {
        return userRepository.count();
    }
}
