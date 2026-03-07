package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.AuthRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.rupp.tola.dev.scoring_management_system.security.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register, verify, login, password reset")
public class AuthController {

	private final AuthService authService;

	@Operation(summary = "Register a new user with fullName , email and password.")
	@PostMapping("/signup")
	public ResponseEntity<SingleResponse<UserResponse>> register(@Valid @RequestBody UserRequest request) {
		UserResponse response = authService.register(request);
		return ResponseEntity.ok(SingleResponse.success("Registration successful! Please verify your email.", response));
	}

	@Operation(summary = "Login with email and password.")
	@PostMapping("/signin")
	public ResponseEntity<SingleResponse<UserResponse>> login(@Valid @RequestBody AuthRequest request) {
		try {
			UserResponse response = authService.login(request);
			return ResponseEntity.ok(SingleResponse.success("Login successful!", response));
		}catch (AuthenticationException ex) {
			throw new RuntimeException("Invalid username and password.", ex);
		}
	}

	@Operation(summary = "Verify email with token.")
	@GetMapping("/signup/verify")
	public ResponseEntity<SingleResponse<UserResponse>> verifyEmail(@RequestParam String token) {
		UserResponse user = authService.verifyEmail(token);
		return ResponseEntity.ok(SingleResponse.success("Email successfully verified!", user));
	}
}