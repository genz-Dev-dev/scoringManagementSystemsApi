package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_role")
@Getter
@Setter
@NoArgsConstructor
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "role_id")
	private UUID id;

	@Column(name = "role_name", unique = true, nullable = false)
	private String name;

	@Column(name = "description", nullable = false, length = 255)
	private String description;

	@Column(name = "status")
	private String status;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<User> users = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "permission_id"))
	private Set<Permission> permissions = new HashSet<>();

}
