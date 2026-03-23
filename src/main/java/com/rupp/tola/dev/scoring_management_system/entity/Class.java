package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_class")
@Getter
@Setter
@NoArgsConstructor
public class Class extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "class_id")
	private UUID id;

	@Column(name = "class_name", updatable = false, nullable = false)
	private String name;
	
	private Boolean status;
	
	@OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Student> students;

}
