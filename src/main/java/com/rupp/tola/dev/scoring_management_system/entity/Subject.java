package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_subject")
@Getter
@Setter
@NoArgsConstructor
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "subject_id")
	private UUID id;

	@Column(name = "subject_code", updatable = false, nullable = false)
	private String subjectCode;

	@Column(name = "subject_name", updatable = false, nullable = false)
	private String subjectName;
}
