package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "classes")
public class Classes {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
	@Column(name = "class_id", columnDefinition = "uuid", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "class_name", updatable = false, nullable = false)
	private String name;
	
	private Boolean status;
}
