package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.SemesterRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.SemesterResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Semester;
import com.rupp.tola.dev.scoring_management_system.exception.DuplicateResourceException;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.SemesterMapper;
import com.rupp.tola.dev.scoring_management_system.repository.SemesterRepository;
import com.rupp.tola.dev.scoring_management_system.service.SemesterService;
import com.rupp.tola.dev.scoring_management_system.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;

    @Override
    public SemesterResponse create(SemesterRequest request) {

        if(semesterRepository.existsBySemesterNo(request.getSemesterNo())) {
            log.info("Semester with no {} already exists", request.getSemesterNo());
            throw new DuplicateResourceException("Semester already exists");
        }

        Semester semester = semesterMapper.toEntity(request);
        semester.setStartAt(Util.convertToLocalDate(request.getStartAt()));
        semester.setEndAt(Util.convertToLocalDate(request.getEndAt()));
        Semester saved = semesterRepository.save(semester);
        log.info("Created Semester with id {}", saved.getId());
        return semesterMapper.toResponse(saved);
    }

    @Override
    public SemesterResponse update(UUID id, SemesterRequest request) {
        Semester semester = this.findByIdOrThrow(id);
        semesterMapper.updateFromRequest(request, semester);
        semester.setStartAt(Util.convertToLocalDate(request.getStartAt()));
        semester.setEndAt(Util.convertToLocalDate(request.getEndAt()));
        Semester saved = semesterRepository.save(semester);
        log.info("Semester with id {} has been updated", id);
        return semesterMapper.toResponse(saved);
    }

    @Override
    public void delete(UUID id) {
        Semester semester = this.findByIdOrThrow(id);
        log.info("Deleting semester with id {}", id);
        semesterRepository.delete(semester);
    }

    @Override
    public List<SemesterResponse> getAll() {
        List<Semester> semesters = semesterRepository.findAll();
        return semesters.stream()
                .map(semesterMapper::toResponse)
                .toList();
    }

    @Override
    public SemesterResponse getById(UUID id) {
        Semester semester = this.findByIdOrThrow(id);
        log.info("Returning Semester with id {}", id);
        return semesterMapper.toResponse(semester);
    }

    private Semester findByIdOrThrow(UUID id) {
        return semesterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found with ID: " + id));
    }
}
