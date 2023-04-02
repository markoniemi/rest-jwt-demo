package org.restjwtdemo.service.login;

import javax.jws.WebService;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.restjwtdemo.model.user.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
@WebService
@Produces({ MediaType.APPLICATION_JSON })
@Path("/auth")
public interface LoginService {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    String login(User userToLogin) throws AuthenticationException;

    @POST
    @Path("/logout")
    void logout(HttpServletRequest request);
}