package com.rupp.tola.dev.scoring_management_system.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class PermissionDTO {

	private UUID id;
	private String name;
	private Boolean status = false;
	
}
