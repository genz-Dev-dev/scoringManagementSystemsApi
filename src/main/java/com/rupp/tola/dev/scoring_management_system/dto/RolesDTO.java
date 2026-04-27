package com.rupp.tola.dev.scoring_management_system.dto;

import java.util.UUID;

import lombok.Data;

import javax.management.relation.RoleStatus;

@Data
public class RolesDTO {
	
	private UUID id;
	private String name;
	private RoleStatus status;
	
}
