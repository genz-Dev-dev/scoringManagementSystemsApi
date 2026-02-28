package com.rupp.tola.dev.scoring_management_system.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rupp.tola.dev.scoring_management_system.service.EmailService;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String from;

	// @Override
	// public void sendVerificationEmail(String email, String verificationToken) {
	// sendEmail(email, verificationToken, "Email Verification",
	// "/req/signup/verify",
	// "Click the button below to verify your email address:");
	// }
	@Override
	public void sendVerificationEmail(String email, String verificationToken) {
		sendEmail(email, verificationToken, "Email Verification", "/req/auth/signup/verify", // ← must match your
																								// controller exactly
				"Click the button below to verify your email address:");
	}

	@Override
	public void sendForgotPasswordEmail(String email, String resetToken) {
		sendEmail(email, resetToken, "Password Reset Request", "/req/reset-password",
				"Click the button below to reset your password:");
	}

	// ------------------------------------------------------------------ private

	private void sendEmail(String to, String token, String subject, String path, String message) {
		try {
			String actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path(path)
					.queryParam("token", token).toUriString();

			String content = buildEmailContent(subject, message, actionUrl);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setFrom(from);
			helper.setText(content, true);

			mailSender.send(mimeMessage);
			log.info("Email sent successfully to {}", to);

		} catch (Exception e) {
			log.error("Failed to send email to {}: {}", to, e.getMessage());
			throw new RuntimeException("Failed to send email", e);
		}
	}

	private String buildEmailContent(String subject, String message, String actionUrl) {
		return """
				<div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px;
				            border-radius: 8px; background-color: #f9f9f9; text-align: center;">
				    <h2 style="color: #333;">%s</h2>
				    <p style="font-size: 16px; color: #555;">%s</p>
				    <a href="%s" style="display: inline-block; margin: 20px 0; padding: 10px 20px;
				                       font-size: 16px; color: #fff; background-color: #007bff;
				                       text-decoration: none; border-radius: 5px;">Proceed</a>
				    <p style="font-size: 14px; color: #777;">Or copy and paste this link into your browser:</p>
				    <p style="font-size: 14px; color: #007bff;">%s</p>
				    <p style="font-size: 12px; color: #aaa;">This is an automated message. Please do not reply.</p>
				</div>
				""".formatted(subject, message, actionUrl, actionUrl);
	}
}