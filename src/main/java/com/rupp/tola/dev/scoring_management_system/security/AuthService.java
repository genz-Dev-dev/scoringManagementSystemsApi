package com.rupp.tola.dev.scoring_management_system.security;

import com.rupp.tola.dev.scoring_management_system.dto.request.ResetPassword;
import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.VerifyOtpRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import jakarta.mail.MessagingException;

import java.util.Map;

public interface AuthService {

	UserResponse register(UserRequest request);

	UserResponse login(UserRequest request);

	UserResponse verifyEmail(String token);

	UserResponse sendForgotPasswordEmail(String email) throws MessagingException;

	Map<String, Object> sendOtpResetPassword(String email);

	Map<String , Object> verifyOtpResetPassword(VerifyOtpRequest request);

	UserResponse resetPassword(ResetPassword resetPassword);

	UserResponse resetPassword(String token, String newPassword);
}
