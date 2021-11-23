package com.example.crudboot.config.security.handler;

import com.example.crudboot.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl
        implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails.getAuthorities().contains(new Role("ADMIN"))) {
            response.sendRedirect("/admin/" + userDetails.getUsername());
        } else {
            response.sendRedirect("/user/" + userDetails.getUsername());
        }
    }
}
