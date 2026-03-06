package com.rupp.tola.dev.scoring_management_system.entity;

import java.util.List;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.enums.RoleName;
import com.rupp.tola.dev.scoring_management_system.enums.RoleStatus;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "role_name", unique = true, nullable = false)
	private RoleName name;

	@Column(name = "status")
	private RoleStatus status = RoleStatus.ACTIVE;

	@ManyToOne
	@JoinColumn(name = "user_id" , referencedColumnName = "user_id")
	private Users users;

}
