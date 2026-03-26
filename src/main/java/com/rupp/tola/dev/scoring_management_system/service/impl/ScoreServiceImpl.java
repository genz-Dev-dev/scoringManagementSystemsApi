package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Score;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.ScoreMapper;
import com.rupp.tola.dev.scoring_management_system.repository.ScoreRepository;
import com.rupp.tola.dev.scoring_management_system.repository.StudentRepository;
import com.rupp.tola.dev.scoring_management_system.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;
    private final ScoreMapper scoreMapper;
    private final StudentRepository studentRepository;

    @Override
    public ScoreResponse create(ScoreRequest request) {

        Score score = scoreMapper.toEntity(request);
        score.setScore(request.getScore());

        Student student =  studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student Not Found with id: " + request.getStudentId()));

        score.setStudent(student);
        Score saved = scoreRepository.save(score);

        return scoreMapper.toDetailResponse(saved);
    }

    @Override
    public ScoreResponse update(UUID id, ScoreRequest request) {

        Score score = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject Not Found: " + id));

        Student student =  studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student Not Found with id: " + request.getStudentId()));

        score.setStudent(student);
        Score updated = scoreRepository.save(score);
        log.info("Updated score id: {}", id);

        return scoreMapper.toDetailResponse(updated);
    }

    @Override
    public ScoreResponse getById(UUID id) {
        Score score = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject Not Found: " + id));
        log.info("Getting score with id: {}", id);
        return scoreMapper.toDetailResponse(score);
    }

    @Override
    public void delete(UUID id) {
        Score score = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject Not Found: " + id));
        log.info("Deleting score with id: {}", id);
        scoreRepository.delete(score);
    }

}
