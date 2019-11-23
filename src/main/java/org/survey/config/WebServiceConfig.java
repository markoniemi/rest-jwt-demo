package org.survey.config;

import javax.annotation.Resource;
import javax.servlet.Servlet;

import org.jvnet.jax_ws_commons.spring.SpringService;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.survey.service.user.UserService;

import com.sun.xml.ws.transport.http.servlet.SpringBinding;
import com.sun.xml.ws.transport.http.servlet.WSSpringServlet;

@Configuration
@ComponentScan(basePackages = "org.survey")
public class WebServiceConfig {
    @Resource
    UserService userService;

    @Bean
    public Servlet jaxwsServlet() {
        return new WSSpringServlet();
    }

    @Bean
    public ServletRegistrationBean<Servlet> servletRegistrationBean() {
        return new ServletRegistrationBean<Servlet>(jaxwsServlet(), "/api/soap/*");
    }

    @Bean()
    public SpringBinding springBinding() throws Exception {
        SpringService service = new SpringService();
        service.setBean(userService);
        SpringBinding binding = new SpringBinding();
        binding.setService(service.getObject());
        binding.setUrl("/api/soap/users");
        return binding;
    }
}