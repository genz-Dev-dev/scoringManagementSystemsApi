package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_semester")
@Getter
@Setter
@NoArgsConstructor
public class Semester {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "semester_id")
	private UUID id;

	@Column(name = "semester_no", updatable = false, nullable = false)
	private int semesterNo;

	@Column(name = "semester_start_at", nullable = false)
	private LocalDate startAt;

	@Column(name = "semester_end_at" , nullable = false)
	private LocalDate endAt;

	@OneToMany(mappedBy = "score" , cascade = CascadeType.ALL)
	private List<Score> scores;

}
