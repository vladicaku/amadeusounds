package com.amadeusounds.angularauth;

import com.amadeusounds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Vladica Jovanovski
 */
@Configuration
@EnableWebSecurity
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AngularAuthenticationProvider angularAuthenticationProvider;

    @Autowired
    AngularAuthenticationFilter angularAuthenticationFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//        http.requiresChannel().anyRequest().requiresSecure();
//
//    http.requiresChannel().anyRequest().requiresSecure();
//            .addFilterBefore(angularAuthenticationFilter, BasicAuthenticationFilter.class)
//            .authorizeRequests()
//                .antMatchers(HttpMethod.POST,"/login")
//                .permitAll()
//                .and()
//            .authorizeRequests()
//                .anyRequest()
//                .authenticated();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(angularAuthenticationProvider);
    }



}
