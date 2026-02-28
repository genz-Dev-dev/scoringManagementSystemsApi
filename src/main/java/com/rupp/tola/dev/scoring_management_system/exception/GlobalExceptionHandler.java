package com.rupp.tola.dev.scoring_management_system.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rupp.tola.dev.scoring_management_system.dto.ApiResponseDto;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponseDto.failure(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.failure(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.failure(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.failure("An unexpected error occurred"));
    }
}

//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//	@ExceptionHandler(ApiException.class)
//	public ResponseEntity<?> handleApiException(ApiException e) {
//		ErrorResponse errorRespose = new ErrorResponse(e.getHttpStatus(), e.getMessage());
//		return ResponseEntity.status(e.getHttpStatus()).body(errorRespose);
//	}
//
//	@ExceptionHandler(IllegalArgumentException.class)
//	public ResponseEntity<Apiresponsedto<Void>> handleIllegalArgument(IllegalArgumentException ex) {
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Apiresponsedto.failure(ex.getMessage()));
//	}
//
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Apiresponsedto<Void>> handleValidation(MethodArgumentNotValidException ex) {
//		String errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
//				.collect(Collectors.joining(", "));
//
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Apiresponsedto.failure(errors));
//	}
//
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<Apiresponsedto<Void>> handleGeneral(Exception ex) {
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//				.body(Apiresponsedto.failure("An unexpected error occurred"));
//	}
//}
