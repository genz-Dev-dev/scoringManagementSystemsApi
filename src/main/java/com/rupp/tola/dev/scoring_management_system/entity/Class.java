package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tbl_class")
@Getter
@Setter
@NoArgsConstructor
public class Class {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "class_id")
	private UUID id;

	@Column(name = "class_name", updatable = false, nullable = false)
	private String name;
	
	private Boolean status;

	@Column(name = "created_at" , nullable = false)
	@CreationTimestamp
	private LocalDate createdAt;


	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDate updatedAt;
}
