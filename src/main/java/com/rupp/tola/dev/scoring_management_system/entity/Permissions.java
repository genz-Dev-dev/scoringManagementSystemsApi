package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import lombok.Data;

@Data
@Entity
public class Permissions {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "permission_id")
	private UUID id;

	@Column(name = "permission_name", length = 100, nullable = false)
	private String name;

	private String permission;


	private Boolean status;


}
