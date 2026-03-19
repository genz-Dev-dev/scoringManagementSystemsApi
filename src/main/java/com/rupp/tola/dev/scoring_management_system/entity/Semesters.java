package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "semesters")
public class Semesters {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "semester_id")
	private UUID id;

	@Column(name = "semester_name", updatable = false, nullable = false)
	private String name;

}
