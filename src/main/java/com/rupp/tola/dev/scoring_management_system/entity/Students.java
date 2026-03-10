package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "students", indexes = { @Index(name = "idx_student_code", columnList = "student_code", unique = true) })
public class Students {

	@Id
<<<<<<< HEAD
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
=======
	@GeneratedValue(strategy = GenerationType.UUID)
>>>>>>> 7fe9c6b319874c8fd8ea935a4508e16203c493e1
	@Column(name = "student_id")
	private UUID id;

	@Column(name = "student_code", updatable = false, nullable = false)
	private String studentCode;

	@Column(name = "name", updatable = false, nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "class_id")
	private Classes classes;

	private Boolean status;
}
