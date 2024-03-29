package org.restjwtdemo.config;

import javax.annotation.Resource;
import org.restjwtdemo.security.JwtAuthenticationFilter;
import org.restjwtdemo.security.JwtAuthorizationFilter;
import org.restjwtdemo.security.UserRepositoryAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Resource
  UserRepositoryAuthenticationProvider userRepositoryAuthenticationProvider;
  @Resource
  UserDetailsService userDetailsService;
  String[] ignoredPaths = {"/api/rest/auth/login/**", "/h2-console/**"};

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable();
    http.authorizeRequests().regexMatchers(".*\\?wsdl").permitAll()//
        .antMatchers(ignoredPaths).permitAll()//
        .anyRequest().authenticated()//
        .and()//
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))//
        .addFilter(new JwtAuthorizationFilter(authenticationManager()));
    // this disables session creation on Spring Security
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    // TODO replace encoder with BCryptPasswordEncoder
    auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(userRepositoryAuthenticationProvider);
  }
}
