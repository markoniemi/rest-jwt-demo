package org.restjwtdemo.service.user;

import java.util.List;

import javax.jws.WebService;

import org.restjwtdemo.model.user.User;

@WebService
public interface UserService {
    List<User> findAll(Integer page, Integer size, String sortBy);

    User create(User user);

    User update(User user);

    User findByUsername(String username);

    User findById(Long id);

    User findByEmail(String email);

    boolean exists(Long id);

    void delete(Long id);

    long count();
}
