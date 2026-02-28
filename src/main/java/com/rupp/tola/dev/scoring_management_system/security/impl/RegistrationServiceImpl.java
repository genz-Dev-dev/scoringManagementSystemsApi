package com.rupp.tola.dev.scoring_management_system.security.impl;

import com.rupp.tola.dev.scoring_management_system.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rupp.tola.dev.scoring_management_system.dto.RegistrationRequestDto;
import com.rupp.tola.dev.scoring_management_system.dto.UserResponseDto;
import com.rupp.tola.dev.scoring_management_system.entity.Users;
import com.rupp.tola.dev.scoring_management_system.mapper.UserMapper;
import com.rupp.tola.dev.scoring_management_system.repository.UsersRepository;
import com.rupp.tola.dev.scoring_management_system.service.EmailService;
import com.rupp.tola.dev.scoring_management_system.security.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements UserService {

	private final UsersRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	private final UserMapper userMapper;
	private final JwtService jwtService;

	@Override
	@Transactional
	public UserResponseDto register(RegistrationRequestDto request) {
		return userRepository.findByEmail(request.getEmail()).map(this::handleExistingUser)
				.orElseGet(() -> createNewUser(request));
	}

	@Override
	@Transactional
	public UserResponseDto verifyEmail(String token) {
		String email = jwtService.extractEmail(token);
		Users user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		if (user.getVerificationToken() == null) {
			throw new IllegalStateException("Token already used or expired");
		}
		if (!jwtService.isTokenExpiration(token) || !user.getVerificationToken().equals(token)) {
			throw new IllegalStateException("Invalid or expired token");
		}
		user.setVerificationToken(null);
		user.setVerified(true);
		userRepository.save(user);

		return userMapper.toResponseDto(user);
	}

	private UserResponseDto handleExistingUser(Users existingUser) {
		if (existingUser.isVerified()) {
			throw new IllegalStateException("User already exists and is verified");
		}
		String token = jwtService.generateToken(existingUser.getEmail());
		existingUser.setVerificationToken(token);
		userRepository.save(existingUser);
		emailService.sendVerificationEmail(existingUser.getEmail(), token);
		return userMapper.toResponseDto(existingUser);
	}

	private UserResponseDto createNewUser(RegistrationRequestDto request) {
		Users user = userMapper.touser(request);
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		user.setPassword(encodedPassword); // → password_hash
//		user.setPasswordLegacy(encodedPassword); // → password column
		String token = jwtService.generateToken(request.getEmail());
		user.setVerificationToken(token);
		Users saved = userRepository.save(user);
		emailService.sendVerificationEmail(saved.getEmail(), token);
		return userMapper.toResponseDto(saved);
	}

//    private UserResponseDto createNewUser(RegistrationRequestDto request) {
//        Users user = userMapper.toEntity(request);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        // ← no role assignment
//        String token = jwtService.generateToken(request.getEmail());
//        user.setVerificationToken(token);
//        Users saved = userRepository.save(user);
//        emailService.sendVerificationEmail(saved.getEmail(), token);
//        return userMapper.toResponseDto(saved);
//    }


//	@Override
//	@Transactional
//	public void verifyEmail(String token) {
//		String email = jwtTokenUtil.extractEmail(token);
//		 Users users = userRepository.findByEmail(email)
//				.orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//		if (users.getVerificationToken() == null) {
//			throw new IllegalStateException("Token already used or expired");
//		}
//		if (!jwtTokenUtil.validateToken(token) || !users.getVerificationToken().equals(token)) {
//			throw new IllegalStateException("Invalid or expired token");
//		}
//		users.setVerificationToken(null);
//		users.setVerified(true);
//		userRepository.save(users);
//	}
}
