package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import com.rupp.tola.dev.scoring_management_system.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupp.tola.dev.scoring_management_system.dto.ApiResponseDto;
import com.rupp.tola.dev.scoring_management_system.dto.ForgotPasswordRequestDto;
import com.rupp.tola.dev.scoring_management_system.dto.ResetPasswordRequestDto;
import com.rupp.tola.dev.scoring_management_system.dto.UserResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/req/auth")
@RequiredArgsConstructor
public class PasswordController {

	private final AuthService passwordService;

	@Operation(summary = "Send forgot password email")
	@PostMapping("/forgotPassword")
	public ResponseEntity<SingleResponse<UserResponse>> forgotPassword(
			@Valid @RequestBody ForgotPasswordRequestDto request) {
		UserResponse response = passwordService.sendForgotPasswordEmail(request.getEmail());
		return ResponseEntity.ok(SingleResponse.success("Password reset email sent. Check your inbox.", response));
	}

	@Operation(summary = "Reset password using token")
	@PostMapping("/resetPassword")
	public ResponseEntity<SingleResponse<UserResponse>> resetPassword(
			@Valid @RequestBody ResetPasswordRequestDto request) {
		UserResponse response = passwordService.resetPassword(request.getToken(), request.getNewPassword());
		return ResponseEntity.ok(SingleResponse.success("Password reset successfully.", response));
	}

















	//	@Operation(summary = "Send forgot password email")
//	@PostMapping("/forgotPassword")
//	public ResponseEntity<ApiResponseDto<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto request) {
//
//		passwordService.sendForgotPasswordEmail(request.getEmail());
//		return ResponseEntity.ok(ApiResponseDto.success("Password reset email sent. Check your inbox."));
//	}

//	public ResponseEntity<ApiResponseDto<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequestDto request) {
//
//		passwordService.resetPassword(request.getToken(), request.getNewPassword());
//		return ResponseEntity.ok(ApiResponseDto.success("Password reset successfully."));
//	}
}