package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "class_id")
	private UUID id;

	@Column(name = "class_name", updatable = false, nullable = false)
	private String name;
	
	private Boolean status;
}
