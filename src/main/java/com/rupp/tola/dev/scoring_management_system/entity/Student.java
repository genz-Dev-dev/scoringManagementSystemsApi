package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "tbl_student",
		indexes = {
		@Index(name = "idx_student_code", columnList = "student_code", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "student_id")
	private UUID id;

	@Column(name = "student_code", updatable = false, nullable = false)
	private String studentCode;

	@Column(name = "kh_first_name", updatable = false, nullable = false)
	private String khFirstName;

	@Column(name = "kh_lastname", updatable = false, nullable = false)
	private String khLastName;

	@Column(name = "en_first_name", updatable = false, nullable = false)
	private String enFirstName;

	@Column(name = "en_last_name", updatable = false, nullable = false)
	private String enLastName;

	@ManyToOne
	@JoinColumn(name = "class_id")
	private Class clazz;

	@Column(name = "gender", length = 1, nullable = false)
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
