package org.restjwtdemo.service.user;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.websocket.server.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.restjwtdemo.model.user.User;

@WebService
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Path("/users")
public interface UserService {
    @GET
    List<User> findAll();

    @GET
    List<User> findAllWithPaging(Integer page, Integer size, String sortBy);

    @POST
    User create(@WebParam(name = "user") User user);

    @PUT
    User update(@WebParam(name = "user") User user);

    @GET
    @Path("/username/{username}")
    User findByUsername(@PathParam("username") @WebParam(name = "username") String username);

    @GET
    @Path("/{id}")
    User findById(@PathParam("id") @WebParam(name = "id") Long id);

    @GET
    @Path("/email/{email}")
    User findByEmail(@PathParam("email") @WebParam(name = "email") String email);

    @GET
    @Path("/exists/{id}")
    boolean exists(@PathParam("id") @WebParam(name = "id") Long id);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") @WebParam(name = "id") Long id);

    @GET
    @Path("/count")
    long count();
}
