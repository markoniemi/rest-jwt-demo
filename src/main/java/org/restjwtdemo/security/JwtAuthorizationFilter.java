package org.restjwtdemo.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = getToken(request);
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
        chain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(JwtToken.AUTHORIZATION_HEADER);
        if (StringUtils.isBlank(header)) {
            return null;
        }
        return header.replace(JwtToken.TOKEN_PREFIX, "");
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String user = JwtToken.verifyToken(token);
        if (StringUtils.isBlank(user)) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }
}