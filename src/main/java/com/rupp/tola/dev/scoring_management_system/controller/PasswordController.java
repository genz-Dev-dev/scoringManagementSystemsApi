package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import com.rupp.tola.dev.scoring_management_system.security.AuthService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
public class PasswordController {

	private final AuthService passwordService;

	@Operation(summary = "Send forgot password email")
	@PostMapping("/forgotPassword")
	public ResponseEntity<SingleResponse<UserResponse>> forgotPassword(
			@Valid @RequestBody ForgotPasswordRequestDto request) throws MessagingException {
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
}