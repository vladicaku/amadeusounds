package com.amadeusounds.angularauth;

import com.amadeusounds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration extends WebSecurityConfigurerAdapter {

    @Autowired
    AngularAuthenticationProvider angularAuthenticationProvider;

    @Autowired
    AngularAuthenticationFilter angularAuthenticationFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable();
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
