package com.rupp.tola.dev.scoring_management_system.entity;
//

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
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
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id")
	private UUID id;

	@Column(name = "user_name", nullable = false, unique = true)
	private String fullName;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password_hash", nullable = false)
	private String password;

	@Column(name = "verification_token")
	private String verificationToken;

	@Column(name = "verified", nullable = false)
	private boolean verified;

	@Column(name = "opt")
	private int opt;

	@Column(name = "expiry_opt")
	private Instant expiryOpt;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "users" , cascade = CascadeType.ALL)
	private List<UploadBatches> uploadBatches;

	@Override
	@NullMarked
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new LinkedList<>();
	}

	@Override
	@NullMarked
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return UserDetails.super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return UserDetails.super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return UserDetails.super.isEnabled();
	}
}
