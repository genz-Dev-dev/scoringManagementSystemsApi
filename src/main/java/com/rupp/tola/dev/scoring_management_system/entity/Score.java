package com.rupp.tola.dev.scoring_management_system.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_score", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "student_id", "semester_id", "subject_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Score extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "score_id")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "semester_id", referencedColumnName = "semester_id"),
			@JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
	})
	private Course course;

	@Column(name = "score")
	private BigDecimal score;

	@Column(name = "score_grade")
	private String grade;

	private boolean status;

}
