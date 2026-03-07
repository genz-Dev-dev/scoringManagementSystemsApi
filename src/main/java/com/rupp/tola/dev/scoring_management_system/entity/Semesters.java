package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

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
