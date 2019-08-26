package com.hrd.somchbab.controller.custom_handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        try {
            if (roles.contains("ROLE_ADMIN"))
                httpServletResponse.sendRedirect("/dashboard");
            else if(roles.contains("ROLE_TEACHER"))
                httpServletResponse.sendRedirect("/teacher");
            else if(roles.contains("ROLE_STUDENT"))
                httpServletResponse.sendRedirect("/student");
            else if(roles.contains("ROLE_DIRECTOR"))
                httpServletResponse.sendRedirect("/director");
        } catch (NullPointerException e) {
            System.out.println("Executed!");
            httpServletResponse.sendRedirect("/login");
        }
    }
}
