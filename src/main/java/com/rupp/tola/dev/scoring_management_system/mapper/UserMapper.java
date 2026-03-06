package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import org.mapstruct.AfterMapping;

import com.rupp.tola.dev.scoring_management_system.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

	Users toEntity(UserRequest request);

	UserResponse toResponse(Users users);

	List<UserResponse> toList(List<Users> users);

	@AfterMapping
	default void encodePassword(@MappingTarget Users users , UserRequest request) {
		String passwordEncoder = new BCryptPasswordEncoder().encode(request.getPassword());
		if(request.getPassword() != null) {
			users.setPassword(passwordEncoder);
		}
	}

}