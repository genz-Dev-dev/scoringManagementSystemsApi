package com.rupp.tola.dev.scoring_management_system.security;

import com.rupp.tola.dev.scoring_management_system.dto.request.AuthRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.ResetPasswordRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.VerifyOtpRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import com.rupp.tola.dev.scoring_management_system.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface AuthService {

	/**
	 * Register new User with fullName , email and Password
	 * @param request
	 * @return UserResponse
	 */
	UserResponse register(UserRequest request);

	/**
	 * Login account with email and password
 	 * @param request
	 * @return UserResponse
	 */
	UserResponse login(AuthRequest request);

	/**
	 * Verify account make sure user email is correct with access token for request
	 * @param token
	 * @return UserResponse
	 */
	UserResponse verifyEmail(String token);

	/**
	 * Send OTP 6 digit number into you email
	 * @param email
	 * @return 6 digit number
	 */
	Map<String, Object> sendOtpResetPassword(String email);

	/**
	 * Verify 6 digit OTP with OTP request by user and then update status isVerifiedOTP for given permission user to update new password
	 * @param userEmail otp
	 * @return
	 */
    Map<String, Object> verifyOtpResetPassword(String token, VerifyOtpRequest request);

    /**
	 * After verify password user can be updated new password with password request
	 * @param resetPasswordRequest
	 * @return
	 */
	UserResponse resetPassword(String token, ResetPasswordRequest request);

	/**
	 * Delete user account with UUID
	 * @param uuid
	 */
	void delete(UUID uuid);

	/**
	 * Retrieve all user has been verified account with pagination
	 */
	Page<UserResponse> findAll(Pageable pageable);

	/**
	 * This method use for set foreign into weak entity set
	 * return Users Object but be care full make sure user is verfied
	 */
	User getUser(UUID uuid);


	/**
	 * This method use to find user has been authentication. For retrieve foreign key
	 * @return User object
	 */
	User getUserAuthenticated();
}
