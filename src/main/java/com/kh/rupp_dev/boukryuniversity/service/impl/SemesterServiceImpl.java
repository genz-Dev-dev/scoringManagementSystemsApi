package com.kh.rupp_dev.boukryuniversity.service.impl;

import com.kh.rupp_dev.boukryuniversity.dto.request.SemesterRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.SemesterResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Semester;
import com.kh.rupp_dev.boukryuniversity.exception.DuplicateResourceException;
import com.kh.rupp_dev.boukryuniversity.exception.ResourceNotFoundException;
import com.kh.rupp_dev.boukryuniversity.mapper.SemesterMapper;
import com.kh.rupp_dev.boukryuniversity.repository.SemesterRepository;
import com.kh.rupp_dev.boukryuniversity.service.SemesterService;
import com.kh.rupp_dev.boukryuniversity.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;

    @Override
    public SemesterResponse create(SemesterRequest request) {
        validateDuplicate(request);

        Semester semester = semesterMapper.toEntity(request);
        semester.setStartDate(Util.convertToLocalDate(request.getStartDate()));
        semester.setEndDate(Util.convertToLocalDate(request.getEndDate()));
        validateDateRange(semester.getStartDate(), semester.getEndDate());
        Semester saved = semesterRepository.save(semester);
        log.info("Created Semester with id {}", saved.getId());
        return semesterMapper.toResponse(saved);
    }

    @Override
    public SemesterResponse update(UUID id, SemesterRequest request) {
        Semester semester = this.findByOrThrow(id);
        validateDuplicate(id, request);
        semesterMapper.updateFromRequest(request, semester);
        semester.setStartDate(Util.convertToLocalDate(request.getStartDate()));
        semester.setEndDate(Util.convertToLocalDate(request.getEndDate()));
        validateDateRange(semester.getStartDate(), semester.getEndDate());
        Semester saved = semesterRepository.save(semester);
        log.info("Semester with id {} has been updated", id);
        return semesterMapper.toResponse(saved);
    }

    @Override
    public void delete(UUID id) {
        Semester semester = this.findByOrThrow(id);
        log.info("Deleting semester with id {}", id);
        semesterRepository.delete(semester);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SemesterResponse> getAll() {
        List<Semester> semesters = semesterRepository.findAll();
        return semesters.stream()
                .map(semesterMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SemesterResponse getById(UUID id) {
        Semester semester = this.findByOrThrow(id);
        log.info("Returning Semester with id {}", id);
        return semesterMapper.toResponse(semester);
    }

    private void validateDuplicate(SemesterRequest request) {
        if (semesterRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Semester name already exists");
        }
    }

    private void validateDuplicate(UUID id, SemesterRequest request) {
        if (semesterRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new DuplicateResourceException("Semester name already exists");
        }
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Semester start date must be before or equal to end date");
        }
    }

    private Semester findByOrThrow(UUID id) {
        return semesterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found with ID: " + id));
    }
}
