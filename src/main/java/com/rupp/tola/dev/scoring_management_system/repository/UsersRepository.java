package com.rupp.tola.dev.scoring_management_system.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rupp.tola.dev.scoring_management_system.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
//	Optional<Users> findByUsername(String username);
	 Optional<Users> findByUsername(String username);

	 Optional<Users> findByEmail(String email);

	 Optional<Users> findByVerificationToken(String token);
}
