package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Permissions {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
	@Column(name = "permission_id", columnDefinition = "uuid", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "permission_name", length = 100, nullable = false)
	private String name;

	private Boolean status;

}
