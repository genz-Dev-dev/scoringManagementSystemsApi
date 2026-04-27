package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.audit.AuditListener;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_student")
@Getter
@Setter
@EntityListeners(AuditListener.class)
public class Student extends BaseEntity {

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

	@Column(name = "enrollment_date")
	private LocalDate enrollmentDate;

	@Column(name = "std_email")
	private String email;

	@Column(name = "std_phoneNumber")
	private String phoneNumber;

	private Boolean status;

	@OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
	private StudentAddress address;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private List<Score> scores;

	public Student() {
		this.status = true;
	}

}
