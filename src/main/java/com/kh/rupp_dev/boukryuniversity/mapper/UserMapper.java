package com.kh.rupp_dev.boukryuniversity.mapper;

import com.kh.rupp_dev.boukryuniversity.dto.request.UserRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.UserResponse;

import com.kh.rupp_dev.boukryuniversity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "id" , ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "verificationToken" , ignore = true)
	@Mapping(target = "verified" , ignore = true)
	@Mapping(target = "role" , ignore = true)
	@Mapping(target = "status" , ignore = true)
	@Mapping(target = "otp" , ignore = true)
	@Mapping(target = "verifiedOtp" , ignore = true)
	@Mapping(target = "expiryOtp" , ignore = true)
	@Mapping(target = "attempt", ignore = true)
	@Mapping(target = "lockTime" , ignore = true)
	@Mapping(target = "createdAt" , ignore = true)
	@Mapping(target = "refreshToken" , ignore = true)
	@Mapping(target = "courses" , ignore = true)
	@Mapping(target = "authorities" , ignore = true)
	User toEntity(UserRequest request);

	@Mapping(target = "role", ignore = true)
	@Mapping(target = "refreshToken" , ignore = true)
	UserResponse toResponse(User user);
}
