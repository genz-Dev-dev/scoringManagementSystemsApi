package com.rupp.tola.dev.scoring_management_system.mapper;

import com.rupp.tola.dev.scoring_management_system.dto.request.UserRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.UserResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Roles;
import com.rupp.tola.dev.scoring_management_system.enums.RoleName;
import com.rupp.tola.dev.scoring_management_system.enums.RoleStatus;
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
public abstract class UserMapper {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
	private JwtService jwtService;

	@Mapping(target = "password", ignore = true)
	public abstract Users toEntity(UserRequest request);

	public abstract UserResponse toResponse(Users users);

	public abstract List<UserResponse> toList(List<Users> users);

	@AfterMapping
	protected void initialize(@MappingTarget Users users, UserRequest request) {
		if (request.getPassword() != null) {
			users.setPassword(passwordEncoder.encode(request.getPassword()));
		}

		String token = jwtService.generateToken(request.getEmail());
		users.setVerificationToken(token);
		users.setVerified(false);

		Roles roles = rolesRepository.findByNameAndStatus(RoleName.ROLE_STAFF, RoleStatus.ACTIVE)
				.orElseGet(() -> {
					Roles role = new Roles();
					role.setName(RoleName.ROLE_STAFF);
					role.setStatus(RoleStatus.ACTIVE);
					return rolesRepository.save(role);
				});

		roles.setUsers(users);
	}
}