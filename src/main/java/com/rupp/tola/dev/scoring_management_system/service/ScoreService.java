package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Score;

import java.util.List;
import java.util.UUID;
public interface ScoreService {

    List<ScoreResponse> create(List<ScoreRequest> requests);

    ScoreResponse update(UUID id, ScoreRequest request);

    ScoreResponse getById(UUID id);

    List<ScoreResponse> getAll();

    void delete(UUID id);

}
