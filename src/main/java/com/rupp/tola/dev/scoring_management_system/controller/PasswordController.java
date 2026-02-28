package com.rupp.tola.dev.scoring_management_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupp.tola.dev.scoring_management_system.dto.ApiResponseDto;
import com.rupp.tola.dev.scoring_management_system.dto.ForgotPasswordRequestDto;
import com.rupp.tola.dev.scoring_management_system.dto.ResetPasswordRequestDto;
import com.rupp.tola.dev.scoring_management_system.dto.UserResponseDto;
import com.rupp.tola.dev.scoring_management_system.security.PasswordService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/req/auth")
@RequiredArgsConstructor
public class PasswordController {

	private final PasswordService passwordService;

	@Operation(summary = "Send forgot password email")
	@PostMapping("/forgotPassword")
	public ResponseEntity<ApiResponseDto<UserResponseDto>> forgotPassword(
			@Valid @RequestBody ForgotPasswordRequestDto request) {
		UserResponseDto user = passwordService.sendForgotPasswordEmail(request.getEmail());
		return ResponseEntity.ok(ApiResponseDto.success("Password reset email sent. Check your inbox.", user));
	}

//	@Operation(summary = "Send forgot password email")
//	@PostMapping("/forgotPassword")
//	public ResponseEntity<ApiResponseDto<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto request) {
//
//		passwordService.sendForgotPasswordEmail(request.getEmail());
//		return ResponseEntity.ok(ApiResponseDto.success("Password reset email sent. Check your inbox."));
//	}

	@Operation(summary = "Reset password using token")
	@PostMapping("/resetPassword")
	public ResponseEntity<ApiResponseDto<UserResponseDto>> resetPassword(
			@Valid @RequestBody ResetPasswordRequestDto request) {
		UserResponseDto user = passwordService.resetPassword(request.getToken(), request.getNewPassword());
		return ResponseEntity.ok(ApiResponseDto.success("Password reset successfully.", user));
	}
//	public ResponseEntity<ApiResponseDto<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequestDto request) {
//
//		passwordService.resetPassword(request.getToken(), request.getNewPassword());
//		return ResponseEntity.ok(ApiResponseDto.success("Password reset successfully."));
//	}
}