package com.rupp.tola.dev.scoring_management_system.dto;

import com.rupp.tola.dev.scoring_management_system.enums.RoleStatus;

import lombok.Data;

@Data
public class RolesDTO {
	
//	private UUID id;
	private String name;
	private RoleStatus status;
	
}
