package com.rupp.tola.dev.scoring_management_system.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rupp.tola.dev.scoring_management_system.entity.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, UUID> {

	Optional<Role> findByName(String name);

	boolean existsByName(String name);

	List<Role> findByStatus(String status);

	Optional<Role> findByNameAndStatus(String name, String name1);

	boolean existsByNameAndIdNot(String roleName, UUID uuid);

	Set<Role> findByIdIn(Set<UUID> uuids);
}