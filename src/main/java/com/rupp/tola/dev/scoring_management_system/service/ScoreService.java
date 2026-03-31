package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ScoreService {

    ScoreResponse create(ScoreRequest request);

    public List<ScoreResponse> createBulk(List<ScoreRequest> requests);

    ScoreResponse updateScoreValue(UUID id, ScoreRequest request);

    ScoreResponse getById(UUID id);

    Page<ScoreResponse> getAll(Pageable pageable);

    List<ScoreResponse> findByStudentId(UUID studentId);

    List<ScoreResponse> findByCourse(UUID semesterId, UUID subjectId);

    ScoreResponse findByStudentAndCourse(UUID studentId, UUID semesterId, UUID subjectId);

    boolean existsByStudentAndCourse(UUID studentId, UUID semesterId, UUID subjectId);

    void delete(UUID id);

}
