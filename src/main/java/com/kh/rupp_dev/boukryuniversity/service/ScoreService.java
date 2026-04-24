package com.kh.rupp_dev.boukryuniversity.service;

import com.kh.rupp_dev.boukryuniversity.dto.request.ScoreRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.ScoreResponse;

import java.util.List;
import java.util.UUID;

public interface ScoreService {

    ScoreResponse create(ScoreRequest request);

    ScoreResponse update(UUID id, ScoreRequest request);

    ScoreResponse getById(UUID id);

    List<ScoreResponse> getAll();

    List<ScoreResponse> findByStudentId(UUID studentId);

    List<ScoreResponse> findByCourse(UUID semesterId, UUID subjectId);

    void delete(UUID id);

}
