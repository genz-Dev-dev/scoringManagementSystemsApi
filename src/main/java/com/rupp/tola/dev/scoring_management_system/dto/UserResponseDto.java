package com.rupp.tola.dev.scoring_management_system.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder // ← missing, MapStruct needs this
@NoArgsConstructor // ← required with @Builder
@AllArgsConstructor
public class UserResponseDto {

	private UUID id;
	private String username;
	private String email;
	private boolean verified;
	private String verificationToken;
	
}