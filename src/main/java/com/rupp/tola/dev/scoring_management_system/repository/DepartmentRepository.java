package com.rupp.tola.dev.scoring_management_system.repository;

import com.rupp.tola.dev.scoring_management_system.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    boolean existsByName(String name);

    boolean existsByCode(String code);

    boolean existsByNameAndIdNot(String name, UUID id);

    boolean existsByCodeAndIdNot(String code, UUID id);
}
