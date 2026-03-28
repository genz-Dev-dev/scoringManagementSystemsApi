package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import org.apache.poi.hssf.record.ScenarioProtectRecord;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.UUID;

public interface ScoreService {

    ScoreResponse create(ScoreRequest request);

    ScoreResponse update(UUID id, ScoreRequest request);

    ScoreResponse getById(UUID id);

    List<ScoreResponse> getAll();

    void delete(UUID id);

}
