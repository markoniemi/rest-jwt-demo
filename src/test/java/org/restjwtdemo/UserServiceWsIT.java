package org.restjwtdemo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restjwtdemo.config.IntegrationTestConfig;
import org.restjwtdemo.model.user.User;
import org.restjwtdemo.service.user.UserService;
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
public class UserServiceWsIT {

    @Test
    public void getUsersWs() throws JsonParseException, JsonMappingException, IOException {
        UserService userService = getUserClient();
        User[] users = userService.findAll();
        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.length);
    }
    public UserService getUserClient() throws MalformedURLException {
        URL wsdlURL = new URL("http://localhost:8082/api/soap/users?wsdl");
        QName qname = new QName("http://user.service.restjwtdemo.org/", "UserService");
        Service service = Service.create(wsdlURL, qname);
        return service.getPort(UserService.class);
    }
}
