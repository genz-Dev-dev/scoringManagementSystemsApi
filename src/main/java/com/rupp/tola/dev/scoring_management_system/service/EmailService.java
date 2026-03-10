package com.rupp.tola.dev.scoring_management_system.service;

import jakarta.mail.MessagingException;

public interface EmailService {
	void sendVerificationEmail(String email, String verificationToken);

	void sendForgotPasswordEmail(String email, String resetToken);

	void sendOtpResetPassword(String email , String otp) throws MessagingException;
}
