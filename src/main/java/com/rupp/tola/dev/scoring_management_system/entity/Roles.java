package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "role_id")
	private UUID id;

	@Column(name = "role_name", unique = true, nullable = false)
	private String name;

	@Column(name = "description" , nullable = false , length = 255)
	private String description;

	@Column(name = "status")
	private String status;

	@ManyToMany(mappedBy = "roles" , cascade = {CascadeType.MERGE , CascadeType.PERSIST})
	private List<Users> users = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "role_permission",
			joinColumns = @JoinColumn(name = "role_id" , referencedColumnName = "role_id"),
			inverseJoinColumns = @JoinColumn(name = "permission_id" , referencedColumnName = "permission_id")
	)
	private Set<Permissions> permissions;

}
