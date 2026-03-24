package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import org.apache.poi.hssf.record.ScenarioProtectRecord;

import java.util.UUID;

public interface ScoreService {
    ScoreResponse create(ScoreRequest request);
    ScoreResponse update(UUID id, ScoreRequest request);
    ScoreResponse getById(UUID id);
    void delete(UUID id);
}
