package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(
		name = "scores", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "student_id", "subject_id", "semester_id" })
})
public class Scores {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
	@Column(name = "score_id", columnDefinition = "uuid", updatable = false, nullable = false)
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
