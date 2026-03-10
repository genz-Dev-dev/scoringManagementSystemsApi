package com.rupp.tola.dev.scoring_management_system.exception;

import com.rupp.tola.dev.scoring_management_system.dto.response.ErrorResponse;
import io.jsonwebtoken.JwtException;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse<Object>> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.badRequest().body(ErrorResponse.error(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse<Object>> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage() != null ? ex.getMessage()
                : "An unexpected " + ex.getClass().getSimpleName() + " occurred";
        log.error("Runtime exception: ", ex);
        return ResponseEntity.badRequest().body(ErrorResponse.error(message));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse<Object>> handleJwtException(JwtException ex) {
        return ResponseEntity.badRequest().body(ErrorResponse.error(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public static ResponseEntity<ErrorResponse<Void>> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.error(ex.getMessage()));
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errorResponse.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest()
                .body(ErrorResponse.error(HttpStatus.BAD_REQUEST, "Method argument not valid", errorResponse));
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMessageNotReadable(
            org.springframework.http.converter.HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        String exactErrorMsg = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.error(HttpStatus.BAD_REQUEST,
                        "Malformed JSON request or invalid accepted values (e.g. invalid Enum). Details: "
                                + exactErrorMsg));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<Void>> handleGeneral(Exception ex) {
        log.error("Unexpected error: ", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.error(ex.getMessage()));
    }
}
