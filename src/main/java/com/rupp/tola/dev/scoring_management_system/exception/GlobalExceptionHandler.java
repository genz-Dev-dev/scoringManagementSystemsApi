package com.rupp.tola.dev.scoring_management_system.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rupp.tola.dev.scoring_management_system.data.ErrorResponse;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse<Object>> handleAuthenticationException(AuthenticationException ex) {
		return ResponseEntity.badRequest().body(ErrorResponse.error(ex.getLocalizedMessage()));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse<Object>> handleRuntimeException(RuntimeException ex) {
		return ResponseEntity.badRequest().body(ErrorResponse.error(ex.getLocalizedMessage()));
	}

	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ErrorResponse<Object>> handleJwtException(JwtException ex) {
		return ResponseEntity.badRequest().body(ErrorResponse.error(ex.getLocalizedMessage()));
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<Object> handleIllegalState(IllegalStateException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponseDto.failure(ex.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.failure(ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {
		String errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.joining(", "));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.failure(errors));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGeneral(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDto.failure(ex.getMessage()));
	}

// custom exception list of students do not have data it  database 
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException exception) {
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(),
				LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

}
	// @ExceptionHandler(Exception.class)
	// public ResponseEntity<ApiResponseDto<Void>> handleGeneral(Exception ex) {
	// log.error("Unexpected error: ", ex);
	// return ResponseEntity
	// .status(HttpStatus.INTERNAL_SERVER_ERROR)
	// .body(ApiResponseDto.failure(ex.getMessage()));
	// }

// @ControllerAdvice
// public class GlobalExceptionHandler {
//
// @ExceptionHandler(ApiException.class)
// public ResponseEntity<?> handleApiException(ApiException e) {
// ErrorResponse errorRespose = new ErrorResponse(e.getHttpStatus(),
// e.getMessage());
// return ResponseEntity.status(e.getHttpStatus()).body(errorRespose);
// }
//
// @ExceptionHandler(IllegalArgumentException.class)
// public ResponseEntity<Apiresponsedto<Void>>
// handleIllegalArgument(IllegalArgumentException ex) {
// return
// ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Apiresponsedto.failure(ex.getMessage()));
// }
//
// @ExceptionHandler(MethodArgumentNotValidException.class)
// public ResponseEntity<Apiresponsedto<Void>>
// handleValidation(MethodArgumentNotValidException ex) {
// String errors =
// ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
// .collect(Collectors.joining(", "));
//
// return
// ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Apiresponsedto.failure(errors));
// }
//
// @ExceptionHandler(Exception.class)
// public ResponseEntity<Apiresponsedto<Void>> handleGeneral(Exception ex) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// .body(Apiresponsedto.failure("An unexpected error occurred"));
// }
// }


