package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "tbl_attendance_control", uniqueConstraints = @UniqueConstraint(
		columnNames = {"student_id", "class_id", "user_id", "created_at"}
))
@Entity
@Data

public class StudentAttendanceControl {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "attendance_control_id")
	private UUID id;
	@ManyToOne
	@JoinColumn(name = "class_id")
	private Class clazz;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

	@ManyToOne
	@JoinColumn(name = "subject_id")
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "request_type")
	private String status;

	@Column(name = "created_at")
	private LocalDate createAt;

	@Column(name = "is_deleted")
	private Boolean idDeleted;
}
