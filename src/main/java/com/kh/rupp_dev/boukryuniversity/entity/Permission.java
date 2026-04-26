package com.kh.rupp_dev.boukryuniversity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tbl_permission")
@Getter
@Setter
@NoArgsConstructor
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "permission_id")
	private UUID id;

	@Column(name = "permission_name" , nullable = false , length = 20)
	private String name;

	@Column(name = "permission_description" , nullable = false , length = 255)
	private String description;

	@Column(name = "permission_module" , nullable = false , length = 50)
	private String module;

	@Column(name = "status")
	private boolean status = true;

	@CreationTimestamp
	@Column(name = "created_at" , nullable = false , updatable = false)
	private LocalDate createdAt;

	@ManyToMany(mappedBy = "permissions" , fetch = FetchType.LAZY , cascade = {CascadeType.MERGE , CascadeType.PERSIST})
	private Set<Role> roles;

}
