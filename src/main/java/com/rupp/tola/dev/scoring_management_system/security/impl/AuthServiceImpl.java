package com.rupp.tola.dev.scoring_management_system.security.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import com.rupp.tola.dev.scoring_management_system.jwt.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rupp.tola.dev.scoring_management_system.entity.Users;
import com.rupp.tola.dev.scoring_management_system.mapper.UserMapper;
import com.rupp.tola.dev.scoring_management_system.repository.UsersRepository;
import com.rupp.tola.dev.scoring_management_system.service.EmailService;
import com.rupp.tola.dev.scoring_management_system.security.AuthService;

import lombok.RequiredArgsConstructor;

import java.security.Security;
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
	private final UserDetailsService userDetailsService;

	@Override
	public UserResponse register(UserRequest request) {
		return userRepository.findByEmail(request.getEmail())
				.map(this::handleExistingUser)
				.orElseGet(() -> createNewUser(request));
	}

	@Override
	public UserResponse verifyEmail(String token) {
		String email = jwtService.extractEmail(token);
		Users user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		if(!jwtService.isTokenExpiration(token) && !token.equals(user.getVerificationToken())) {
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
		Users user = userMapper.toEntity(request);
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		user.setPassword(encodedPassword);
		String token = jwtService.generateToken(request.getEmail());
		user.setVerificationToken(token);
		Users saved = userRepository.save(user);
		emailService.sendVerificationEmail(saved.getEmail(), token);
		return userMapper.toResponse(saved);
	}

	@Override
	public UserResponse sendForgotPasswordEmail(String email) throws MessagingException {
		Users user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("No account found with that email"));

		int randomOtp = ThreadLocalRandom.current().nextInt(100000 , 1000000);
		emailService.sendOtpResetPassword(email , String.valueOf(randomOtp));

		String resetToken = jwtService.generateToken(user.getEmail());
		user.setVerificationToken(resetToken);
		userRepository.save(user);
		emailService.sendForgotPasswordEmail(user.getEmail(), resetToken);
		return userMapper.toResponse(user);
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
