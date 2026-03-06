package com.rupp.tola.dev.scoring_management_system.entity;
//

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

	@Column(name = "user_name", length = 50, nullable = false, unique = true)
	private String fullName;

	@Column(name = "email", length = 50, nullable = false, unique = true)
	private String email;

	@Column(name = "password_hash", nullable = false)
	private String password;

	@Column(name = "verification_token")
	private String verificationToken;

	@Column(name = "verified", nullable = false)
	private boolean verified;

	@Column(name = "otp")
	private String otp;

	@Column(name = "is_otp_verified")
	private boolean isOtpVerified;

	@Column(name = "expiry_opt")
	private Instant expiryOtp;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
	private List<UploadBatches> uploadBatches;

	@OneToMany(mappedBy = "users",fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	private List<Roles> roles;

	@Override
	@NullMarked
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(role -> {
					String roleName = role.getName().name();
					if (!roleName.startsWith("ROLE_")) {
						roleName = "ROLE_" + roleName;
					}
					return new SimpleGrantedAuthority(roleName);
				})
				.toList();
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
