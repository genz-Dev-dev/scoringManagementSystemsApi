package com.rupp.tola.dev.scoring_management_system.service;

import ch.qos.logback.core.read.ListAppender;
import com.rupp.tola.dev.scoring_management_system.dto.request.SubjectRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.SubjectResponse;
import com.rupp.tola.dev.scoring_management_system.repository.SubjectRepository;
import org.openxmlformats.schemas.drawingml.x2006.chart.impl.CTUnsignedIntImpl;

import java.util.List;
import java.util.UUID;

public interface SubjectService {
    SubjectResponse create(SubjectRequest request);
    SubjectResponse getByUuid(UUID id);
    List<SubjectResponse> getAll();
    SubjectResponse update(UUID id, SubjectRequest request);
    void delete(UUID id);
}
