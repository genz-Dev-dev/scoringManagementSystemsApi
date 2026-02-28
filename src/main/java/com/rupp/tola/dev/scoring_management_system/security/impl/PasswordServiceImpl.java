package com.rupp.tola.dev.scoring_management_system.security.impl;

import com.rupp.tola.dev.scoring_management_system.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rupp.tola.dev.scoring_management_system.dto.UserResponseDto;
import com.rupp.tola.dev.scoring_management_system.entity.Users;
import com.rupp.tola.dev.scoring_management_system.mapper.UserMapper;
import com.rupp.tola.dev.scoring_management_system.repository.UsersRepository;
import com.rupp.tola.dev.scoring_management_system.service.EmailService;
import com.rupp.tola.dev.scoring_management_system.security.PasswordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

	private final UsersRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	private final JwtService jwtService;
	private final UserMapper userMapper;

	@Override
	@Transactional
	public UserResponseDto sendForgotPasswordEmail(String email) {
		Users user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("No account found with that email"));

		String resetToken = jwtService.generateToken(user.getEmail());
		user.setVerificationToken(resetToken);
		userRepository.save(user);
		emailService.sendForgotPasswordEmail(user.getEmail(), resetToken);
		return userMapper.toResponseDto(user);
	}

	@Override
	@Transactional
	public UserResponseDto resetPassword(String token, String newPassword) {
//        String email = jwtTokenUtil.extractEmail(token);
//
//        Users user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        if (user.getVerificationToken() == null || !jwtTokenUtil.validateToken(token)
//                || !user.getVerificationToken().equals(token)) {
//            throw new IllegalStateException("Invalid or expired reset token");
//        }
//
//        user.setPassword(passwordEncoder.encode(newPassword));
//        user.setVerificationToken(null);
//        userRepository.save(user);
//        String email = jwtTokenUtil.extractEmail(token);

		String email = jwtService.extractEmail(token);

		Users user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		if (user.getVerificationToken() == null || !jwtService.isTokenExpiration(token)
				|| !user.getVerificationToken().equals(token)) {
			throw new IllegalStateException("Invalid or expired reset token");
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		user.setVerificationToken(null);
		userRepository.save(user);

		return userMapper.toResponseDto(user);
	}
}