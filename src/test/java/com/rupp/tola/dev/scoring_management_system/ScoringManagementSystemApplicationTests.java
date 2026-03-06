package com.rupp.tola.dev.scoring_management_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD
// import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rupp.tola.dev.scoring_management_system.mapper.UserMapper;
import com.rupp.tola.dev.scoring_management_system.repository.UsersRepository;
import com.rupp.tola.dev.scoring_management_system.service.EmailService;
<<<<<<< Updated upstream
import com.rupp.tola.dev.scoring_management_system.backup.JwtTokenUtil_Backup;
=======
// import com.rupp.tola.dev.scoring_management_system.backup.Backup_JwtTokenUtil;
>>>>>>> Stashed changes

@SpringBootTest(properties = "app.cors.allowed-origins=http://localhost:4200")
class ScoringManagementSystemApplicationTests {

	// @MockBean
	// private UsersRepository userRepository;

	// @MockBean
	// private PasswordEncoder passwordEncoder;

	// @MockBean
	// private EmailService emailService;

	// @MockBean
	// private UserMapper userMapper;

<<<<<<< Updated upstream
	@MockBean
	private JwtTokenUtil_Backup jwtTokenUtil;
=======
	// @MockBean
	// private Backup_JwtTokenUtil jwtTokenUtil;
>>>>>>> Stashed changes

	@MockBean
	private PasswordService passwordService; // <<< Add this

	@Test
	void contextLoads() {
	}
}
=======
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ScoringManagementSystemApplicationTests {

    @Test
    void contextLoads() {
    	
    }
}
>>>>>>> e06fde8e06e8fb7fdfe51a0b8703cc55ed048a77
