package com.rupp.tola.dev.scoring_management_system.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupp.tola.dev.scoring_management_system.payload.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomeAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        ErrorResponse<Object> errorResponse = ErrorResponse.error(HttpStatus.UNAUTHORIZED,"Please put your access token or Email and Password to Authenticate.");
        String jsonString = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonString);
    }
}
