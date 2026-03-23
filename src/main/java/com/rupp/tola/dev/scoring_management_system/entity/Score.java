package com.rupp.tola.dev.scoring_management_system.entity;

import java.math.BigDecimal;
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
public class Score extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "score_id")
	private UUID id;

	@Column(name = "score" , nullable = false)
	private BigDecimal score;

	@Column(name = "version", nullable = false)
	private Integer version = 1;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id" , referencedColumnName = "student_id")
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_id" , referencedColumnName = "subject_id")
	private Subject subject;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "semester_id" , referencedColumnName = "semester_id")
	private Semester semester;


}
