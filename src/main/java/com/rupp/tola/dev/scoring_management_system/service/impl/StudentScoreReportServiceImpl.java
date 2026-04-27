package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.response.StudentScoreResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Score;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentScoreMapper;
import com.rupp.tola.dev.scoring_management_system.repository.ScoreRepository;
import com.rupp.tola.dev.scoring_management_system.repository.StudentRepository;
import com.rupp.tola.dev.scoring_management_system.service.StudentScoreReportService;
import com.rupp.tola.dev.scoring_management_system.specification.StudentScoreReportSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentScoreReportServiceImpl implements StudentScoreReportService {
    private final StudentRepository studentRepository;
    private final ScoreRepository scoreRepository;
    private final StudentScoreMapper mapper;

    @Override
    public List<StudentScoreResponse> getStudentScoreReport() {

        Specification<Score> spec = StudentScoreReportSpecification.report();

        List<Score> scores = scoreRepository.findAll(spec);

        return mapper.toDtoList(scores);
    }
}
