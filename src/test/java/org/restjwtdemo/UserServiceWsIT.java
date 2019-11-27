package org.restjwtdemo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restjwtdemo.config.IntegrationTestConfig;
import org.restjwtdemo.model.user.User;
import org.restjwtdemo.security.JwtToken;
import org.restjwtdemo.service.user.UserService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestJwtDemoApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = IntegrationTestConfig.class))
public class UserServiceWsIT {

    @Test
    @Ignore
    public void getUsers() throws JsonParseException, JsonMappingException, IOException {
        UserService userService = getUserClient();
        List<User> users = userService.findAll(null, null, null);
        Assert.assertNotNull(users);
        Assert.assertEquals(10, users.size());
    }
    @Test
    @Ignore
    public void getUsersWithPageAndSize() throws JsonParseException, JsonMappingException, IOException {
        UserService userService = getUserClient();
        List<User> users = userService.findAll(2 , 2, null);
        Assert.assertNotNull(users);
        Assert.assertEquals(2, users.size());
    }

    public UserService getUserClient() throws MalformedURLException {
        URL wsdlURL = new URL("http://localhost:8082/api/soap/users?wsdl");
        QName qname = new QName("http://user.service.restjwtdemo.org/", "UserService");
        Service service = Service.create(wsdlURL, qname);
        UserService port = service.getPort(UserService.class);
        // TODO how to set http request headers to soap port
        ((BindingProvider) port).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS,
                Collections.singletonMap("Authorization", "Bearer " + JwtToken.createToken("admin1")));
        return port;
    }
}
