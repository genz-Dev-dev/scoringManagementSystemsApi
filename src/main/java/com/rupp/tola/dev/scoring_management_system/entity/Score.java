package com.rupp.tola.dev.scoring_management_system.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.audit.AuditListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
		name = "tbl_score",
		uniqueConstraints = {@UniqueConstraint(name = "uk_score_unique", columnNames = {"student_id", "semester_id", "subject_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditListener.class)
public class Score extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "score_id")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_id")
	private Subject subject;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "semester_id")
	private Semester semester;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "score")
	private BigDecimal score;

	@Column(name = "version")
	private Integer version;



	@Column(name = "is_deleted")
	private boolean status;

}
