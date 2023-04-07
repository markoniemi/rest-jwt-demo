package org.restjwtdemo;

import static org.junit.Assert.assertNotNull;
import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import org.junit.Test;
import org.restjwtdemo.model.user.Role;
import org.restjwtdemo.model.user.User;
import org.restjwtdemo.security.JwtToken;
import org.restjwtdemo.service.login.LoginService;

public class LoginServiceFeignIT extends AbstractIntegrationTestBase {
    @Resource
    private LoginService loginService;

    @Test
    public void login() throws AuthenticationException {
        User user = new User("admin1", "admin", "email", Role.ROLE_USER);
        String token = loginService.login(user);
        assertNotNull(token);
        assertNotNull(JwtToken.verifyToken(token));
    }
}
