package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Roles {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
	@Column(name = "role_id", columnDefinition = "uuid", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "role_name", length = 50, nullable = false)
	private String name;
	@Column(name = "status", columnDefinition = "boolean default true")
	private Boolean status;
}
