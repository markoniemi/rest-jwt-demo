package org.restjwtdemo.config;

import javax.annotation.Resource;

import org.restjwtdemo.security.UserRepositoryAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    UserRepositoryAuthenticationProvider userRepositoryAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin().loginPage("/login").loginProcessingUrl("/j_spring_security_check")
                .defaultSuccessUrl("/user/users").usernameParameter("j_username").passwordParameter("j_password")
                .failureUrl("/login?error=true").permitAll();
        http.logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/login").permitAll();
        http.authorizeRequests().antMatchers("/", "/home", "/api/**", "/h2-console/**", "/static/**", "/webjars/**").permitAll()
                .anyRequest().authenticated();
        http.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userRepositoryAuthenticationProvider);
    }
}