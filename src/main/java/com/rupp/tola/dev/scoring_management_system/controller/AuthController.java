package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.ResetPassword;
import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.VerifyOtpRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rupp.tola.dev.scoring_management_system.security.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register, verify, login, password reset")
public class AuthController {

	private final AuthService userService;

	@Operation(summary = "Register a new user")
	@PostMapping("/signup")
	public ResponseEntity<SingleResponse<UserResponse>> register(@Valid @RequestBody UserRequest request) {
		UserResponse response = userService.register(request);
		return ResponseEntity.ok(SingleResponse.success("Registration successful! Please verify your email.", response));
	}

	@Operation(summary = "Login with email and password.")
	@PostMapping("/signin")
	public ResponseEntity<SingleResponse<UserResponse>> login(@Valid @RequestBody UserRequest request) {
		try {
			UserResponse response = userService.login(request);
			return ResponseEntity.ok(SingleResponse.success("Login successful!", response));
		}catch (AuthenticationException ex) {
			throw new RuntimeException("Invalid username and password.", ex);
		}
	}

	@Operation(summary = "Verify email with token")
	@GetMapping("/signup/verify")
	public ResponseEntity<SingleResponse<UserResponse>> verifyEmail(@RequestParam String token) {
		UserResponse user = userService.verifyEmail(token);
		return ResponseEntity.ok(SingleResponse.success("Email successfully verified!", user));
	}

	@Operation(summary = "Send reset password otp for changing password.")
	@PostMapping("/send-otp")
	public ResponseEntity<SingleResponse<Map<String , Object>>> sendOtp(@RequestBody Map<String , String> email) {
		var response = userService.sendOtpResetPassword(email.get("email"));
		return ResponseEntity.ok(SingleResponse.success("Reset password successful!", response));
	}

	@Operation(summary = "Verify 6 digit number from email for changing new password.")
	@PostMapping("/verify-otp")
	public ResponseEntity<SingleResponse<Map<String , Object>>> verifyOtp(@RequestBody VerifyOtpRequest request) {
		var response = userService.verifyOtpResetPassword(request);
		return ResponseEntity.ok(SingleResponse.success("Reset Password successful!", response));
	}

	@Operation(summary = "Update new password before verify opt from email.")
	@PostMapping("/reset-password")
	public ResponseEntity<SingleResponse<UserResponse>> resetPassword(@Valid @RequestBody ResetPassword resetPassword) {
		var response = userService.resetPassword(resetPassword);
		return ResponseEntity.ok(SingleResponse.success("Reset Password successful!", response));
	}


}