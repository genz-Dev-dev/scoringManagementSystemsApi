package com.rupp.tola.dev.scoring_management_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.rupp.tola.dev.scoring_management_system.repository.UsersRepository;
@SpringBootTest(properties = "app.cors.allowed-origins=http://localhost:4200")

class ScoringManagementSystemApplicationTests {

	@MockitoBean
	private UsersRepository userRepository;

//	@MockitoBean
//	private PasswordEncoder passwordEncoder;
//
//	@MockBean
//	private EmailService emailService;
//
//	@MockBean
//	private UserMapper userMapper;
//
//	@MockBean
//	private Backup_JwtTokenUtil jwtTokenUtil;

//	@MockBean
//	private PasswordService passwordService; // <<< Add this

	@Test
	void contextLoads() {

	}
}