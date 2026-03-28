package com.rupp.tola.dev.scoring_management_system.security.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.AuthRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.ResetPasswordRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.VerifyOtpRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import com.rupp.tola.dev.scoring_management_system.entity.RefreshToken;
import com.rupp.tola.dev.scoring_management_system.entity.Role;
import com.rupp.tola.dev.scoring_management_system.entity.User;
import com.rupp.tola.dev.scoring_management_system.enums.RoleName;
import com.rupp.tola.dev.scoring_management_system.enums.Status;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.jwt.JwtService;
import com.rupp.tola.dev.scoring_management_system.repository.RoleRepository;
import com.rupp.tola.dev.scoring_management_system.service.RefreshTokenService;
import com.rupp.tola.dev.scoring_management_system.utils.Util;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rupp.tola.dev.scoring_management_system.mapper.UserMapper;
import com.rupp.tola.dev.scoring_management_system.repository.UserRepository;
import com.rupp.tola.dev.scoring_management_system.service.EmailService;
import com.rupp.tola.dev.scoring_management_system.security.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	private final UserMapper userMapper;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final RoleRepository roleRepository;
	private final RefreshTokenService refreshTokenService;

	@Override
	@Transactional
	public UserResponse register(UserRequest request) {
		return userRepository.findByEmail(request.getEmail())
				.map(this::handleExistingUser)
				.orElseGet(() -> createNewUser(request));
	}

	@Override
	public UserResponse login(AuthRequest request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));
		if(!user.isStatus()) {
			log.info("User has been freeze with status: {}" , user.isStatus());
			throw new RuntimeException("User has been freeze with status: " + user.isStatus());
		}
		Authentication authToken = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authToken);
		User saved = userRepository.save(user);
		UserResponse response = toResponse(saved);
		response.setVerificationToken(jwtService.generateToken(user.getEmail()));
		return response;
	}

	@Override
	public UserResponse verifyEmail(String token) {
		String userEmail = jwtService.extractEmail(token);
		User user = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
		if (jwtService.isTokenExpiration(token) && !token.equals(user.getVerificationToken())) {
			throw new JwtException("Jwt token is Expiry or isn't correct.");
		}

		if (user.isVerified()) {
			throw new IllegalStateException("User account is already verify.");
		}

		user.setVerificationToken(null);
		user.setVerified(true);
		userRepository.save(user);

		return toResponse(user);
	}

	private UserResponse handleExistingUser(User existingUser) {
		if (existingUser.isVerified()) {
			throw new IllegalStateException("User already exists and is verified");
		}
		String token = jwtService.generateToken(existingUser.getEmail());
		existingUser.setVerificationToken(token);
		userRepository.save(existingUser);
		emailService.sendVerificationEmail(existingUser.getEmail(), token);
		return toResponse(existingUser);
	}

	private UserResponse createNewUser(UserRequest request) {
		User user = userMapper.toEntity(request);
		user.setStatus(true);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		RefreshToken refresh = refreshTokenService.create();
		refresh.setUser(user);
		user.setRefreshToken(refresh);
		user.setRefreshToken(refresh);
		String token = jwtService.generateToken(user.getEmail());
		user.setVerificationToken(token);
		user.setVerified(false);

		Role role = roleRepository.findByNameAndStatus(request.getRole(), Status.ACTIVE.name())
						.orElseThrow(()-> new ResourceNotFoundException("Role not found with name: " + RoleName.ROLE_STAFF.name()));

		user.setRole(role);
		log.info("New user created: {}", user);
		User saved = userRepository.save(user);
		emailService.sendVerificationEmail(saved.getEmail(), saved.getVerificationToken());
		return toResponse(saved);
	}

	@Override
	public Map<String, Object> sendOtpResetPassword(String email) {
		try {
			String otp = Util.generateOtp();
			emailService.sendOtpResetPassword(email, otp);
			User user = userRepository.findByEmail(email)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
			user.setOtp(otp);
			user.setExpiryOtp(Instant.now().plusSeconds(600));
			log.info("OTP have been you email please check you box.");
			userRepository.save(user);
			return Map.of("email", user.getEmail(), "otp", otp);
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong");
		}
	}

	@Override
	public Map<String, Object> verifyOtpResetPassword(String token, VerifyOtpRequest request) {
		String userEmail = jwtService.extractEmail(token);
		User user = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
		if (request.getOtp() == null || !request.getOtp().equals(user.getOtp())) {
			throw new RuntimeException("OTP is not matching.");
		}
		if (user.getExpiryOtp().isBefore(Instant.now())) {
			throw new RuntimeException("OTP is expired.");
		}
		user.setVerifiedOtp(true);
		user.setOtp(null);
		log.info("OTP verified successfully.");
		userRepository.save(user);
		return Map.of("userEmail", user.getEmail(), "verifiedOtp", user.isVerifiedOtp());
	}

	@Override
	public UserResponse resetPassword(String token, ResetPasswordRequest request) {
		String userEmail = jwtService.extractEmail(token);
		User user = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

		if (!user.isVerifiedOtp()) {
			throw new RuntimeException("Your OTP is not verified.");
		}

		if (user.getExpiryOtp().isBefore(Instant.now())) {
			user.setVerifiedOtp(false);
			user.setExpiryOtp(null);
			userRepository.save(user);
			throw new RuntimeException("Your OTP is expired you cannot change password.");
		}

		user.setVerifiedOtp(false);
		user.setExpiryOtp(null);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		log.info("Password have been reset by user email {}.", userEmail);
		userRepository.save(user);
		return toResponse(user);
	}

	@Override
	public void delete(UUID uuid) {
		User user = userRepository.findById(uuid)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + uuid));
		log.info("Delete user with UUID: {}", uuid);
		userRepository.delete(user);
	}

	@Override
	public Page<UserResponse> findAll(Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);
		return users.map(this::toResponse);
	}

	@Override
	public User getUser(UUID uuid) {
		User user = userRepository.findById(uuid)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with UUID: " + uuid));
		if (!user.isVerified()) {
			throw new RuntimeException("User isn't verified account.");
		}
		return user;
	}

	@Override
	public void updateStatus(UUID uuid, String status) {
		User user = this.findByOrThrow(uuid);
		user.setStatus(Boolean.parseBoolean(status));
		userRepository.save(user);
	}

	@Override
	public User getUserAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        return userRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("User not found with Email: " + authentication.getName()));
	}

	private UserResponse toResponse(User user) {
		UserResponse response = userMapper.toResponse(user);
		response.setRefreshToken(user.getRefreshToken().getToken());
		response.setRole(user.getRole().getName());
		return response;
	}

	private User findByOrThrow(UUID uuid) {
		return userRepository.findById(uuid)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with UUID: " + uuid));
	}

}
