package com.rupp.tola.dev.scoring_management_system;

import com.rupp.tola.dev.scoring_management_system.service.EmailService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@SpringBootApplication
public class ScoringManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(ScoringManagementSystemApplication.class, args);
	}
}
