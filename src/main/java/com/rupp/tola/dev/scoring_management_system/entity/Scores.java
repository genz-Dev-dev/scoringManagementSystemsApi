package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(
		name = "scores", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "student_id", "subject_id", "semester_id" })
})
public class Scores {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "score_id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Students students;

	@ManyToOne
	@JoinColumn(name = "subject_id")
	private Subjects subjects;

	@ManyToOne
	@JoinColumn(name = "semester_id")
	private Semesters semesters;

	@Column(name = "score")
	private Double score;

	@Column(name = "version", nullable = false)
	private Integer version = 1;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users users;

	@Column(name = "create_at", updatable = false, nullable = false)
	private LocalDateTime create;

	@Column(name = "upload_at", updatable = false, nullable = false)
	private LocalDateTime upload;
}
