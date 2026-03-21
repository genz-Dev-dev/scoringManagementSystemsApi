package com.rupp.tola.dev.scoring_management_system.repository;

import java.util.Optional;
import java.util.UUID;

import com.rupp.tola.dev.scoring_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {

	boolean existsByEmail(String email);

	 Optional<User> findByFullName(String fullName);

	 Optional<User> findByEmail(String email);

	 Optional<User> findByVerificationToken(String token);
}
