package org.restjwtdemo;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import org.apache.cxf.transport.http.HTTPException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.InstanceOf;
import org.restjwtdemo.model.user.User;
import org.restjwtdemo.security.JwtToken;
import org.restjwtdemo.service.user.UserService;
import org.springframework.security.access.AccessDeniedException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class UserServiceWsIT extends AbstractIntegrationTestBase {
  private UserService userService;

  @Before
  public void setUp() throws MalformedURLException {
    userService = getUserService();
    setAuthorizationHeader(userService, "Bearer " + JwtToken.createToken("admin1"));
  }

  @Test
  public void getUsers() throws JsonParseException, JsonMappingException, IOException {
    List<User> users = userService.findAll();
    Assert.assertNotNull(users);
    Assert.assertEquals(21, users.size());
  }

  @Test
  public void getUsersWithoutAuthorizationFails()
      throws JsonParseException, JsonMappingException, IOException {
    setAuthorizationHeader(userService, null);
    try {
      List<User> users = userService.findAll();
      fail();
    } catch (WebServiceException e) {
      assertInstanceOf(HTTPException.class, e.getCause());
    }
  }

  public UserService getUserService() throws MalformedURLException {
    URL wsdlURL = new URL("http://localhost:8082/api/soap/users?wsdl");
    QName qname = new QName("http://user.service.restjwtdemo.org/", "UserService");
    Service service = Service.create(wsdlURL, qname);
    return service.getPort(UserService.class);
  }

  public static void setAuthorizationHeader(Object service, String jwtHeader) {
    Map<String, List<String>> requestHeaders = new HashMap<>();
    if (jwtHeader != null) {
      requestHeaders.put("Authorization", Arrays.asList(jwtHeader));
    }
    ((BindingProvider) service).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS,
        requestHeaders);
  }
}
