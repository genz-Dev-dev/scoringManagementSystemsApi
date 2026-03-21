package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;

import com.rupp.tola.dev.scoring_management_system.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "password", ignore = true)
	@Mapping(target = "verificationToken" , ignore = true)
	@Mapping(target = "verified" , ignore = true)
	@Mapping(target = "role" , ignore = true)
	User toEntity(UserRequest request);

	@Mapping(target = "role", ignore = true)
	@Mapping(target = "refreshToken" , ignore = true)
	UserResponse toResponse(User user);
}
