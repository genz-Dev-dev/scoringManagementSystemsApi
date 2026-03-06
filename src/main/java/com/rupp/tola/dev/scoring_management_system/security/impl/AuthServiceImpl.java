package com.rupp.tola.dev.scoring_management_system.security.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.ResetPassword;
import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.VerifyOtpRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import com.rupp.tola.dev.scoring_management_system.jwt.JwtService;
import com.rupp.tola.dev.scoring_management_system.util.Util;
import io.jsonwebtoken.JwtException;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rupp.tola.dev.scoring_management_system.entity.Users;
import com.rupp.tola.dev.scoring_management_system.mapper.UserMapper;
import com.rupp.tola.dev.scoring_management_system.repository.UsersRepository;
import com.rupp.tola.dev.scoring_management_system.service.EmailService;
import com.rupp.tola.dev.scoring_management_system.security.AuthService;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UsersRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	private final UserMapper userMapper;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@Override
	public UserResponse register(UserRequest request) {
		return userRepository.findByEmail(request.getEmail())
				.map(this::handleExistingUser)
				.orElseGet(() -> createNewUser(request));
	}

	@Override
	public UserResponse login(UserRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
				)
		);
		Users users = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));

		String token = jwtService.generateToken(request.getEmail());
		users.setVerificationToken(token);
		Users saved = userRepository.save(users);
		return userMapper.toResponse(saved);
	}

	@Override
	public UserResponse verifyEmail(String token) {
		String email = jwtService.extractEmail(token);
		Users user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		if (!jwtService.isTokenExpiration(token) && !token.equals(user.getVerificationToken())) {
			throw new JwtException("Jwt token is Expiry or isn't correct.");
		}

		if (user.isVerified()) {
			throw new IllegalStateException("User account is already verify.");
		}

		user.setVerificationToken(null);
		user.setVerified(true);
		userRepository.save(user);

		return userMapper.toResponse(user);
	}

	private UserResponse handleExistingUser(Users existingUser) {
		if (existingUser.isVerified()) {
			throw new IllegalStateException("User already exists and is verified");
		}
		String token = jwtService.generateToken(existingUser.getEmail());
		existingUser.setVerificationToken(token);
		userRepository.save(existingUser);
		emailService.sendVerificationEmail(existingUser.getEmail(), token);
		return userMapper.toResponse(existingUser);
	}

	private UserResponse createNewUser(UserRequest request) {
		Users users = userMapper.toEntity(request);
		Users saved = userRepository.save(users);

		emailService.sendVerificationEmail(saved.getEmail(), saved.getVerificationToken());
		return userMapper.toResponse(saved);
	}

	@Override
	public UserResponse sendForgotPasswordEmail(String email) throws MessagingException {
		Users user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("No account found with that email"));

		int randomOtp = ThreadLocalRandom.current().nextInt(100000, 1000000);
		emailService.sendOtpResetPassword(email, String.valueOf(randomOtp));

		String resetToken = jwtService.generateToken(user.getEmail());
		user.setVerificationToken(resetToken);
		userRepository.save(user);
		emailService.sendForgotPasswordEmail(user.getEmail(), resetToken);
		return userMapper.toResponse(user);
	}

	@Override
	public Map<String, Object> sendOtpResetPassword(String email) {
		try {
			String otp = Util.generateOtp();
			emailService.sendOtpResetPassword(email, otp);
			Users users = userRepository.findByEmail(email)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
			users.setOtp(otp);
			users.setExpiryOtp(Instant.now().plusSeconds(600));
			userRepository.save(users);
			return Map.of("email", users.getEmail(), "otp", otp);
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong");
		}
	}

	@Override
	public Map<String, Object> verifyOtpResetPassword(VerifyOtpRequest request) {
		Users users = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));
		if (request.getOtp() == null || !request.getOtp().equals(users.getOtp())) {
			throw new RuntimeException("OTP is not matching.");
		}
		if (users.getExpiryOtp().isBefore(Instant.now())) {
			throw new RuntimeException("OTP is expired.");
		}
		users.setOtpVerified(true);
		users.setOtp(null);
		users.setExpiryOtp(null);
		userRepository.save(users);
		return Map.of("email", users.getEmail() ,"isVerified", users.isVerified());
	}

	@Override
	public UserResponse resetPassword(ResetPassword resetPassword) {
		Users users = userRepository.findByEmail(resetPassword.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + resetPassword.getEmail()));
		if(!users.isVerified()) {
			throw new RuntimeException("User is already verified.");
		}
		users.setVerified(false);
		users.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
		userRepository.save(users);
		return userMapper.toResponse(users);
	}

	@Override
	public UserResponse resetPassword(String token, String newPassword) {

		String email = jwtService.extractEmail(token);

		Users user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		if (user.getVerificationToken() == null || jwtService.isTokenExpiration(token)
				|| !user.getVerificationToken().equals(token)) {
			throw new IllegalStateException("Invalid or expired reset token");
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		user.setVerificationToken(null);
		userRepository.save(user);

		return userMapper.toResponse(user);
	}

}
