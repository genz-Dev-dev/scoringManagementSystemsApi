package com.rupp.tola.dev.scoring_management_system.dto;

import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.entity.Classes;

import lombok.Data;

@Data
public class StudentsDTO {
	
	private String studentCode;
	private String name;
//	private Classes classes;
	private UUID classId;
	private Boolean status = false;
	
}
