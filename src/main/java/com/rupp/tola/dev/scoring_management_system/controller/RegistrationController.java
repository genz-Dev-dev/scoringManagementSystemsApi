package com.rupp.tola.dev.scoring_management_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rupp.tola.dev.scoring_management_system.dto.ApiResponseDto;
import com.rupp.tola.dev.scoring_management_system.dto.RegistrationRequestDto;
import com.rupp.tola.dev.scoring_management_system.dto.UserResponseDto;
import com.rupp.tola.dev.scoring_management_system.security.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/req/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register, verify, login, password reset")
public class RegistrationController {

	private final UserService userService;

	@Operation(summary = "Register a new user")
	@PostMapping("/signup")
	public ResponseEntity<ApiResponseDto<UserResponseDto>> register(
			@Valid @RequestBody RegistrationRequestDto request) {

		UserResponseDto user = userService.register(request);
		return ResponseEntity.ok(ApiResponseDto.success("Registration successful! Please verify your email.", user));
	}

	@Operation(summary = "Verify email with token")
	@GetMapping("/signup/verify")
	public ResponseEntity<ApiResponseDto<UserResponseDto>> verifyEmail(@RequestParam String token) {
		UserResponseDto user = userService.verifyEmail(token);
		return ResponseEntity.ok(ApiResponseDto.success("Email successfully verified!", user));
	}

//    @Operation(summary = "Verify email with token")
//    @GetMapping("/signup/verify")
//    public ResponseEntity<ApiResponseDto<Void>> verifyEmail(
//            @RequestParam String token) {
//
//        userService.verifyEmail(token);
//        return ResponseEntity.ok(ApiResponseDto.success("Email successfully verified!"));
//    }
}