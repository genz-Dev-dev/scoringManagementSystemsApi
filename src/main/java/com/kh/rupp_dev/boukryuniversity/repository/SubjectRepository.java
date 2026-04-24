package com.kh.rupp_dev.boukryuniversity.repository;

import com.kh.rupp_dev.boukryuniversity.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, UUID id);

    @Query(value = "SELECT nextval('subject_code_seq')" ,nativeQuery = true)
    Long getNextSequenceSubject();
}
