package com.rupp.tola.dev.scoring_management_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rupp.tola.dev.scoring_management_system.entity.Subject;
@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID> {

}
