package com.kh.rupp_dev.boukryuniversity.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_score")
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
	private Course course;

	@Column(name = "score")
	private BigDecimal score;

	@Column(name = "score_grade")
	private String grade;

	private boolean status;

}
