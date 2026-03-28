package com.rupp.tola.dev.scoring_management_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rupp.tola.dev.scoring_management_system.entity.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, UUID> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}
