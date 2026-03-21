package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_score", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "student_id", "subject_id", "semester_id" })
})
@Getter
@Setter
@NoArgsConstructor
public class Score {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "score_id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "subject_id")
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "semester_id")
	private Semester semester;

	@Column(name = "score")
	private Double score;

	@Column(name = "version", nullable = false)
	private Integer version = 1;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "create_at", updatable = false, nullable = false)
	private LocalDateTime create;

	@Column(name = "upload_at", updatable = false, nullable = false)
	private LocalDateTime upload;
}
