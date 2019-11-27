package org.restjwtdemo;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restjwtdemo.config.IntegrationTestConfig;
import org.restjwtdemo.model.user.User;
import org.restjwtdemo.service.user.UserClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestJwtDemoApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = IntegrationTestConfig.class))
@Log4j2
public class UserServiceFeignIT {
    @Resource
    private UserClient userClient;
    @Test
    public void getUsers() throws JsonParseException, JsonMappingException, IOException {
        List<User> users = userClient.findAll(null, null, null);
        Assert.assertNotNull(users);
        Assert.assertEquals(10, users.size());
    }
    @Test
    public void getUsersWithPageAndSize() throws JsonParseException, JsonMappingException, IOException {
        List<User> users = userClient.findAll(2 , 2, null);
        Assert.assertNotNull(users);
        Assert.assertEquals(2, users.size());
    }
}
