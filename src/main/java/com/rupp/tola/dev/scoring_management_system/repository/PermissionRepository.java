package com.rupp.tola.dev.scoring_management_system.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rupp.tola.dev.scoring_management_system.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID>{
    Optional<Permission> findByName(String name);
    boolean existsByNameAndModule(String name , String module);
    List<Permission> findByModule(String module);
    Set<Permission> findByIdIn(List<UUID> id);
}
