package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.enums.RoleName;
import com.rupp.tola.dev.scoring_management_system.enums.RoleStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "role_name", unique = true, nullable = false)
	private RoleName name;

	@Column(name = "status")
	private RoleStatus status = RoleStatus.ACTIVE;

	@ManyToMany(mappedBy = "roles")
	private List<Users> users;

}
