package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

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

}
