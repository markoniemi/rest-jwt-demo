package org.restjwtdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.restjwtdemo.model.user.Role;
import org.restjwtdemo.model.user.User;
import org.restjwtdemo.service.user.UserService;
import org.springframework.validation.BindException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class UserServiceFeignIT extends AbstractIntegrationTestBase {
  @Resource
  private UserService userService;
    
    @Test
    public void getUsers() throws JsonParseException, JsonMappingException, IOException {
        List<User> users = userService.findAll();
        Assert.assertNotNull(users);
        Assert.assertEquals(21, users.size());
    }

    @Test
    public void create() throws BindException {
        User user = new User("username", "password", "email", Role.ROLE_USER);
        User savedUser = userService.create(user);
        savedUser = userService.findByUsername("username");
        assertEquals("username", savedUser.getUsername());
        assertEquals("email", savedUser.getEmail());
//        assertTrue(userService.exists(savedUser.getId()));
        savedUser = userService.findById(savedUser.getId());
        assertEquals("username", user.getUsername());
        userService.delete(savedUser.getId());
        savedUser = userService.findByUsername("username");
        assertNull(savedUser);
    }
}
