package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students", indexes = { @Index(name = "idx_student_code", columnList = "student_code", unique = true) })
public class Students {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "student_id")
	private UUID id;

	@NotBlank
	@Column(name = "student_code", updatable = false, nullable = false)
	private String studentCode;

	@NotBlank
	@Column(name = "kh_first_name", updatable = false, nullable = false)
	private String khFirstName;

	@NotBlank
	@Column(name = "kh_lastname", updatable = false, nullable = false)
	private String khLastName;

	@NotBlank
	@Column(name = "en_first_name", updatable = false, nullable = false)
	private String enFirstname;

	@NotBlank
	@Column(name = "en_last_name", updatable = false, nullable = false)
	private String enlastName;

	@ManyToOne
	@JoinColumn(name = "class_id")
	private Classes classes;

	@NotNull
	@Pattern(regexp = "[MF]", message = "Gender must be M or F")
	@Column(name = "M/F", length = 1, nullable = false)
	private String gender;

	@Column(name = "std_dob")
	private LocalDate dateOfBirth;

	@Column(name = "std_email")
	private String email;

	@Column(name = "std_phoneNumber")
	private String phoneNumber;

	@Embedded
	private Address address;

	private Boolean status;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

}
