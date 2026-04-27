package com.rupp.tola.dev.scoring_management_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rupp.tola.dev.scoring_management_system.entity.Classes;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, UUID> {
	List<Classes> findBystatus(Boolean status);
}
