package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Roles;
import com.rupp.tola.dev.scoring_management_system.enums.RoleName;
import com.rupp.tola.dev.scoring_management_system.enums.Status;
import com.rupp.tola.dev.scoring_management_system.jwt.JwtService;
import com.rupp.tola.dev.scoring_management_system.repository.RolesRepository;
import org.mapstruct.AfterMapping;

import com.rupp.tola.dev.scoring_management_system.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "password", ignore = true)
	@Mapping(target = "verificationToken" , ignore = true)
	@Mapping(target = "verified" , ignore = true)
	@Mapping(target = "roles" , ignore = true)

	Users toEntity(UserRequest request);

	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "refreshToken" , ignore = true)
	UserResponse toResponse(Users users);
}
