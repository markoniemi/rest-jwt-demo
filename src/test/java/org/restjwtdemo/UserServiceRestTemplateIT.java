package org.restjwtdemo;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restjwtdemo.config.IntegrationTestConfig;
import org.restjwtdemo.config.RestRequestInterceptor;
import org.restjwtdemo.model.user.Role;
import org.restjwtdemo.model.user.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestJwtDemoApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = IntegrationTestConfig.class))
public class UserServiceRestTemplateIT {
    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    @Resource
    private String url;
    @Resource
    private RestRequestInterceptor requestInterceptor;

    @Before
    public void setUp() {
        testRestTemplate.getRestTemplate().setInterceptors(Collections.singletonList(requestInterceptor));
    }

    @Test
    @Ignore
    public void login1() {
        LoginForm loginForm = new LoginForm("admin1", "admin");
        ResponseEntity<LoginForm> response = testRestTemplate.postForEntity(url + "/login",
                new HttpEntity<LoginForm>(loginForm), LoginForm.class);
        List<String> list = response.getHeaders().get("Authorization");
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        String token = list.get(0);
        Assert.assertTrue(token.startsWith("Bearer"));
    }

    @Test
    public void login() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", "admin1");
        map.add("password", "admin");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = testRestTemplate.postForEntity(url + "/login", request, String.class);
        List<String> list = response.getHeaders().get("Authorization");
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        String token = list.get(0);
        Assert.assertTrue(token.startsWith("Bearer"));
    }

    @Test
    public void findUsers() throws JsonParseException, JsonMappingException, IOException {
        User[] users = testRestTemplate.getForObject(url + "/api/rest/users", User[].class);
        Assert.assertNotNull(users);
        Assert.assertEquals(21, users.length);
    }

    @Test
    @Ignore
    public void findUsersWithPaging() throws JsonParseException, JsonMappingException, IOException {
        ResponseEntity<String> responseString = testRestTemplate.getForEntity(url + "/api/rest/users?page=0&size=2",
                String.class);
        Assert.assertNotNull(responseString);
        User[] users = testRestTemplate.getForObject(url + "/api/rest/users", User[].class);
        Assert.assertNotNull(users);
        Assert.assertEquals(2, users.length);
    }

    @Test
    public void findById() throws JsonParseException, JsonMappingException, IOException {
        User user = testRestTemplate.getForObject(url + "/api/rest/users/1", User.class);
        Assert.assertEquals("admin1", user.getUsername());
    }

    @Test
//    @Ignore
    public void findByIdUsername() throws JsonParseException, JsonMappingException, IOException {
        User user = testRestTemplate.getForObject(url + "/api/rest/users/username/admin1", User.class);
        Assert.assertEquals("admin1", user.getUsername());
    }
//    public void findByIdUsername() throws JsonParseException, JsonMappingException, IOException {
//        User user = testRestTemplate.getForObject(url + "/api/rest/users?username=admin1", User.class);
//        Assert.assertEquals("admin1", user.getUsername());
//    }

    @Test
    public void create() throws JsonProcessingException {
        User user = new User("test", "test", "email", Role.ROLE_USER);
        testRestTemplate.postForObject(url + "/api/rest/users", new HttpEntity<User>(user), User.class);
        User[] users = testRestTemplate.getForObject(url + "/api/rest/users", User[].class);
        Assert.assertEquals(22, users.length);
        user = testRestTemplate.getForObject(url + "/api/rest/users/username/test", User.class);
        Assert.assertEquals("test", user.getUsername());
        testRestTemplate.delete(url + "/api/rest/users/"+user.getId());
        user = testRestTemplate.getForObject(url + "/api/rest/users/username/test", User.class);
    }

    @Data
    @RequiredArgsConstructor
    private class LoginForm {
        @NonNull
        private String username;
        @NonNull
        private String password;

    }
}
