package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "classes")
public class Classes {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "class_id")
	private UUID id;

	@Column(name = "class_name", updatable = false, nullable = false)
	private String name;
	
	private Boolean status;
}
