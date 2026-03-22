package com.rupp.tola.dev.scoring_management_system.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupp.tola.dev.scoring_management_system.data.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomeAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8");
        ErrorResponse<Object> errorResponse = ErrorResponse.error(HttpStatus.FORBIDDEN ,"You don't have enough permissions to access this resource.");
        String jsonString = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonString);
    }
}
