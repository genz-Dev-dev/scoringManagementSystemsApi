package com.rupp.tola.dev.scoring_management_system.security;

import com.rupp.tola.dev.scoring_management_system.dto.RegistrationRequestDto;
import com.rupp.tola.dev.scoring_management_system.dto.UserResponseDto;

public interface UserService {

	UserResponseDto register(RegistrationRequestDto request);

//    void verifyEmail(String token);
	UserResponseDto verifyEmail(String token);
}
