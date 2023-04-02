package org.restjwtdemo.service.user;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import org.restjwtdemo.model.user.Role;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserSearchForm {
    @QueryParam("role")
    @FormParam("role")
    private Role role;
    @QueryParam("email")
    @FormParam("email")
    private String email;
    @QueryParam("username")
    @FormParam("username")
    private String username;
}
