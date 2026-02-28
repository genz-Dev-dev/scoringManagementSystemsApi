package com.rupp.tola.dev.scoring_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rupp.tola.dev.scoring_management_system.dto.RegistrationRequestDto;
import com.rupp.tola.dev.scoring_management_system.dto.UserResponseDto;
import com.rupp.tola.dev.scoring_management_system.entity.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "verificationToken", ignore = true)
	@Mapping(target = "verified", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
	@Mapping(target = "username", source = "username")
	Users touser(RegistrationRequestDto dto);

	@Mapping(target = "id", source = "id")
	@Mapping(target = "username", source = "username")
	@Mapping(target = "email", source = "email")
	@Mapping(target = "verified", source = "verified")
	@Mapping(target = "verificationToken", source = "verificationToken")
	UserResponseDto toResponseDto(Users user);
}