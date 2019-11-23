package org.restjwtdemo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restjwtdemo.RestJwtDemoApp;
import org.restjwtdemo.config.IntegrationTestConfig;
import org.restjwtdemo.config.RestRequestInterceptor;
import org.restjwtdemo.model.user.User;
import org.restjwtdemo.service.user.UserClient;
import org.restjwtdemo.service.user.UserService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestJwtDemoApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = IntegrationTestConfig.class))
@Log4j2
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
    public void getUsers() throws JsonParseException, JsonMappingException, IOException {
        ResponseEntity<String> responseString = testRestTemplate.getForEntity(url + "/api/rest/users", String.class);
        Assert.assertNotNull(responseString);
        List<User> users = parseResponse(responseString);
        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.size());
    }

    @Test
    public void getUser() throws JsonParseException, JsonMappingException, IOException {
        User user = testRestTemplate.getForObject(url + "/api/rest/users?username=admin1", User.class);
        Assert.assertEquals("admin1", user.getUsername());
    }


    private List<User> parseResponse(ResponseEntity<String> responseString)
            throws IOException, JsonParseException, JsonMappingException {
        return new ObjectMapper().readValue(responseString.getBody(), new TypeReference<List<User>>() {
        });
    }
}
