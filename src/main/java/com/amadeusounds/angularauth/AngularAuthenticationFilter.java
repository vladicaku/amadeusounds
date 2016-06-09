package com.amadeusounds.angularauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Angular Authentication Filter
 * To be improved - highly suggested (implement configuration, success and failure handlers, etc.)
 * This is a prototype version
 *
 * @author Vladica Jovanovski
 */
@Component
public class AngularAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    AngularAuthenticationProvider angularAuthenticationProvider;

    private RequestMatcher loginMatcher;
    private RequestMatcher authenticatedMatcher;

    public AngularAuthenticationFilter() {
        loginMatcher = new AntPathRequestMatcher("/login");
        authenticatedMatcher = new AndRequestMatcher(new AntPathRequestMatcher("/api/admin/**"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (loginMatcher.matches(httpServletRequest)) {
            AngularAuthenticationToken authentication;
            if (httpServletRequest.getMethod().equals("POST")) {
                String email = httpServletRequest.getHeader("email");
                String password = httpServletRequest.getHeader("password");
                email = email == null ? "" : email;
                password = password == null ? "" : password;
                authentication = new AngularAuthenticationToken(email, password);
                angularAuthenticationProvider.authenticate(authentication);

                if (authentication.isAuthenticated()) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    httpServletResponse.setHeader("angular-authentication-token", authentication.getToken());
                    loginSuccessful(httpServletRequest, httpServletResponse, filterChain);
                } else {
                    loginFailed(httpServletRequest, httpServletResponse, filterChain);
                }
            } else if (httpServletRequest.getMethod().equals("OPTIONS")) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
            else {
                loginFailed(httpServletRequest, httpServletResponse, filterChain);
            }
        }
        else if (!authenticatedMatcher.matches(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        else if(authenticatedMatcher.matches(httpServletRequest)) {
            /**
             * Check the validity of the token
             */
            AngularAuthenticationToken authentication;
            String token = httpServletRequest.getHeader("angular-authentication-token");
            if (token != null) {
                authentication = new AngularAuthenticationToken();
                authentication.setToken(token);
                angularAuthenticationProvider.authenticate(authentication);
                if (authentication.isAuthenticated()) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    authorized(httpServletRequest, httpServletResponse, filterChain);
                } else {
                    unauthorized(httpServletRequest, httpServletResponse, filterChain);
                }
            }
            else {
                unauthorized(httpServletRequest, httpServletResponse, filterChain);
            }
        }
    }

    private void authorized(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void unauthorized(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //httpServletResponse.sendRedirect(loginUrl);
    }

    private void loginFailed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }

    private void loginSuccessful(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        //filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

//    @Autowired
//    UserService userService;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        String token = ((HttpServletRequest)servletRequest).getHeader("angular-authentication-token");
//        AngularAuthenticationToken authentication;
//
//        if (token != null) {
//            User user = userService.findByToken(token);
//            if (user != null) {
//                authentication = new AngularAuthenticationToken(user.getEmail(), "");
//                authentication.setAuthenticated(true);
//                authentication.setUser(user);
//                authentication.setToken(token);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }
//        }
//
//        if (((HttpServletRequest)servletRequest).getMethod().equals("POST")) {
//            String email = servletRequest.getParameter("email");
//            String password = servletRequest.getParameter("password");
//
//            if (email == null || password == null) {
//                throw new BadCredentialsException("No email and password");
//            } else {
//                authentication = new AngularAuthenticationToken(email, password);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }
//        }
//
//
//        throw new BadCredentialsException("Not logged in");
//    }
//
//    @Override
//    public void destroy() {
//
//    }
}
