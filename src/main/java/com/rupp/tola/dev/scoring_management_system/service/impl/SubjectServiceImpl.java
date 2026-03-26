package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.SubjectRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.SubjectResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Subject;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.SubjectMapper;
import com.rupp.tola.dev.scoring_management_system.repository.SubjectRepository;
import com.rupp.tola.dev.scoring_management_system.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper mapper;

    @Override
    public SubjectResponse create(SubjectRequest request) {
        Subject subject = mapper.toEntity(request);
        subject.setScores(new ArrayList<>());
        Subject saved = subjectRepository.save(subject);
        log.info("Created subject {}", subject);
        return mapper.toResponse(saved);
    }

    @Override
    public SubjectResponse getByUuid(UUID id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject Not Found: " + id));
        log.info("GetById subject id: {}", id);
        return mapper.toResponse(subject);
    }

    @Override
    public List<SubjectResponse> getAll() {
        List<Subject> subjects = subjectRepository.findAll();
        log.info("Subject found with all {}", subjects);
        return subjects.stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public SubjectResponse update(UUID id, SubjectRequest request) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject Not Found: " + id));
        subject.setSubjectCode(request.getSubjectCode());
        subject.setSubjectName(request.getSubjectName());
        Subject update = subjectRepository.save(subject);
        log.info("Updated subject id: {}", id);
        return mapper.toResponse(update);
    }

    @Override
    public void delete(UUID id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject Not Found: " + id));
        log.info("Deleted subject  id: {}", id);
        subjectRepository.delete(subject);
    }
}
