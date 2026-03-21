package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.dto.request.RefreshTokenRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ErrorResponse;
import com.rupp.tola.dev.scoring_management_system.dto.response.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.AuthRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import com.rupp.tola.dev.scoring_management_system.jwt.JwtService;
import com.rupp.tola.dev.scoring_management_system.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.rupp.tola.dev.scoring_management_system.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register, verify, login, password reset")
public class AuthController {

	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	private final JwtService jwtService;

	@Operation(summary = "Register a new user with fullName , email and password.")
	@PostMapping("/signup")
	public ResponseEntity<SingleResponse<UserResponse>> register(@Valid @RequestBody UserRequest request) {
		UserResponse response = authService.register(request);
		ResponseCookie cookie = ResponseCookie.from("token" , response.getRefreshToken())
				.path("/")
				.httpOnly(true)
				.secure(false)
				.maxAge(Duration.ofDays(7))
				.sameSite("Strict")
				.build();
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(SingleResponse.success("Registration successful! Please verify your email.", response));
	}

	@Operation(summary = "Login with email and password.")
	@PostMapping("/signin")
	public ResponseEntity<SingleResponse<UserResponse>> login(@Valid @RequestBody AuthRequest request) {
		try {
			UserResponse response = authService.login(request);
			ResponseCookie cookie = ResponseCookie.from("token" , response.getRefreshToken())
					.path("/")
					.httpOnly(true)
					.secure(false)
					.maxAge(Duration.ofDays(7))
					.sameSite("Strict")
					.build();
			return ResponseEntity.status(HttpStatus.OK)
					.header(HttpHeaders.SET_COOKIE , cookie.toString())
					.body(SingleResponse.success("Login successful!", response));
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

	@Operation(summary = "Retrieve new access token.")
	@GetMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
		return refreshTokenService.findByToken(request.getToken())
				.map(refresh -> {
					if(refreshTokenService.verify(refresh)) {
						String token = jwtService.generateToken(refresh.getUser().getEmail());
						return ResponseEntity.ok(SingleResponse.success("Refresh successful!", Map.of("token" , token)));
					}
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(ErrorResponse.error("Invalid refresh token."));
				})
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
						ErrorResponse.error("User un authentication.")
				));
	}

}