package com.rupp.tola.dev.scoring_management_system.security;

import com.rupp.tola.dev.scoring_management_system.dto.RegistrationRequestDto;
import com.rupp.tola.dev.scoring_management_system.dto.UserResponseDto;
import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;

public interface AuthService {

	UserResponse register(UserRequest request);

	UserResponse verifyEmail(String token);

	UserResponse sendForgotPasswordEmail(String email);

	UserResponse resetPassword(String token, String newPassword);
}
