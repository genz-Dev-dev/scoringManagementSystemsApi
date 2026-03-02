package com.rupp.tola.dev.scoring_management_system.entity;
//

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import org.hibernate.annotations.UuidGenerator;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "users")
//public class Users {
//	@Id
//	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
//	@Column(name = "user_id", columnDefinition = "uuid", updatable = false, nullable = false)
//	private UUID id;
//
//	@Column(name = "user_name", updatable = false, nullable = false)
//	private String name;
//
//	@Column(name = "password_hash", updatable = false, nullable = false)
//	private String password;
//
//	@ManyToOne
//	@JoinColumn(name = "role_id")
//	private Roles roles;
//
////	private boolean isAccountNonExpired;
////	
////	private boolean isAccountNonLocked;
////	
////	private boolean isCredentialsNonExpired;
////	
////	private boolean isEnabled;
//
//	@Column(name = "created_at", updatable = false, nullable = false)
//	private LocalDateTime date;
//
//}
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id")
	private UUID id;

	@Column(name = "user_name", nullable = false, unique = true)
	private String username;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password_hash", nullable = false)
	private String password;

	@Column(name = "verification_token")
	private String verificationToken;

	@Column(name = "verified", nullable = false)
	private boolean verified;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "users" , cascade = CascadeType.ALL)
	private List<UploadBatches> uploadBatches;
}
