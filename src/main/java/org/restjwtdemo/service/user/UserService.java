package org.restjwtdemo.service.user;

import javax.jws.WebService;

import org.restjwtdemo.model.user.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
@WebService
public interface UserService {
    User[] findAll();

    User create(User user);

    User update(User user);

    User findByUsername(String username);

    User findById(Long id);

    User findByEmail(String email);

    boolean exists(Long id);

    void delete(Long id);

    long count();
}
