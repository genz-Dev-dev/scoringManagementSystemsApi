package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.dto.response.StudentScoreResponse;

import java.util.List;

public interface StudentScoreReportService {
    List<StudentScoreResponse> getStudentScoreReport();
}
