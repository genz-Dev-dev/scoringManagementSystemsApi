package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "students" ,
indexes = {
		@Index(name = "idx_student_code" , columnList = "student_code" , unique = true)
}
)
public class Students {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
	@Column(name = "student_id", columnDefinition = "uuid", updatable = false, nullable = false)
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
