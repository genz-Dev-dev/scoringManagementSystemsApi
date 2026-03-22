package com.rupp.tola.dev.scoring_management_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.rupp.tola.dev.scoring_management_system.repository.UserRepository;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
@SpringBootTest(properties = "app.cors.allowed-origins=http://localhost:4200")

class ScoringManagementSystemApplicationTests {

	@MockitoBean
	private UserRepository userRepository;

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