package com.rupp.tola.dev.scoring_management_system.security.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.AuthRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.ResetPasswordRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.VerifyOtpRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import com.rupp.tola.dev.scoring_management_system.jwt.JwtService;
import com.rupp.tola.dev.scoring_management_system.util.Util;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.UUID;

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
	public UserResponse login(AuthRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
				)
		);
		Users users = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));
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
		log.info("New user created: {}", users);
		Users saved = userRepository.save(users);
		emailService.sendVerificationEmail(saved.getEmail(), saved.getVerificationToken());
		return userMapper.toResponse(saved);
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
			log.info("OTP have been you email please check you box.");
			userRepository.save(users);
			return Map.of("email", users.getEmail(), "otp", otp);
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong");
		}
	}
	@Override
	public Map<String, Object> verifyOtpResetPassword(String token, VerifyOtpRequest request) {
		String userEmail = jwtService.extractEmail(token);
		Users users = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
		if (request.getOtp() == null || !request.getOtp().equals(users.getOtp())) {
			throw new RuntimeException("OTP is not matching.");
		}
		if (users.getExpiryOtp().isBefore(Instant.now())) {
			throw new RuntimeException("OTP is expired.");
		}
		users.setVerifiedOtp(true);
		users.setOtp(null);
		log.info("OTP verified successfully.");
		userRepository.save(users);
		return Map.of("userEmail", users.getEmail() ,"verifiedOtp", users.isVerifiedOtp());
	}

	@Override
	public UserResponse resetPassword(String token , ResetPasswordRequest request) {
		String userEmail = jwtService.extractEmail(token);
		Users users = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

		if(!users.isVerifiedOtp()) {
			throw new RuntimeException("Your OTP is not verified.");
		}

		if(users.getExpiryOtp().isBefore(Instant.now())) {
			users.setVerifiedOtp(false);
			userRepository.save(users);
			throw new RuntimeException("Your OTP is expired you cannot change password.");
		}

		users.setVerifiedOtp(false);
		users.setExpiryOtp(null);
		users.setPassword(passwordEncoder.encode(request.getPassword()));
		log.info("Password have been reset by user email {}." , userEmail);
		userRepository.save(users);
		return userMapper.toResponse(users);
	}

	@Override
	public void delete(UUID uuid) {
		Users users = userRepository.findById(uuid)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + uuid));
		log.info("Delete user with UUID: {}" , uuid);
		userRepository.delete(users);
	}

	@Override
	public Page<UserResponse> findAll(Pageable pageable) {
		Page<Users> users = userRepository.findAll(pageable);
		return users.map(userMapper::toResponse);
	}

	@Override
	public Users getUser(UUID uuid) {
		Users users = userRepository.findById(uuid)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with UUID: " + uuid));
		if(!users.isVerified()) {
			throw new RuntimeException("User isn't verified account.");
		}
		return users;
	}

}
