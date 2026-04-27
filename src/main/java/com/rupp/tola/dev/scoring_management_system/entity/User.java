package com.rupp.tola.dev.scoring_management_system.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;


import com.rupp.tola.dev.scoring_management_system.audit.AuditListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tbl_user")
@Data
@NoArgsConstructor
@EntityListeners(AuditListener.class)
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id")
	private UUID id;

	@Column(name = "user_name", length = 25, nullable = false, unique = true)
	private String fullName;

	@Column(name = "email", length = 25, nullable = false, unique = true)
	private String email;

	@Column(name = "password_hash", nullable = false , length = 255)
	private String password;

	@Column(name = "verification_token")
	private String verificationToken;

	@Column(name = "verified", nullable = false)
	private boolean verified;

	@Column(name = "otp")
	private String otp;

	@Column(name = "is_otp_verified")
	private boolean verifiedOtp;

	@Column(name = "expiry_opt")
	private Instant expiryOtp;

	@Column(name = "user_status" , nullable = false)
	private boolean status;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@OneToOne(mappedBy = "user" , cascade = CascadeType.ALL)
	private RefreshToken refreshToken;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id" , referencedColumnName = "role_id")
	private Role role;

	@OneToMany(mappedBy = "instructor" ,  cascade = CascadeType.ALL)
	private List<Course> courses;

	@Override
	@NullMarked
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(role.getName()));
		role.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
        return authorities;
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
