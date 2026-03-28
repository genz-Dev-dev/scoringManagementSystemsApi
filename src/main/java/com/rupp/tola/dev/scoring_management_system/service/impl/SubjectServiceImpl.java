package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.SubjectRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.SubjectResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Department;
import com.rupp.tola.dev.scoring_management_system.entity.Subject;
import com.rupp.tola.dev.scoring_management_system.exception.DuplicateResourceException;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.SubjectMapper;
import com.rupp.tola.dev.scoring_management_system.repository.DepartmentRepository;
import com.rupp.tola.dev.scoring_management_system.repository.SubjectRepository;
import com.rupp.tola.dev.scoring_management_system.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public SubjectResponse create(SubjectRequest request) {
        validateDuplicate(request);

        Subject subject = subjectMapper.toEntity(request);
        subject.setDepartment(findDepartmentById(request.getDepartmentId()));

        Subject saved = subjectRepository.save(subject);
        log.info("Subject created with id {}", saved.getId());
        return subjectMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponse> getAll() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectResponse getById(UUID id) {
        Subject subject = findByOrThrow(id);
        log.info("Subject found with id {}", id);
        return subjectMapper.toResponse(subject);
    }

    @Override
    public SubjectResponse update(UUID id, SubjectRequest request) {
        Subject subject = findByOrThrow(id);
        validateDuplicate(id, request);

        subjectMapper.updateFromRequest(request, subject);
        subject.setDepartment(findDepartmentById(request.getDepartmentId()));

        Subject saved = subjectRepository.save(subject);
        log.info("Subject updated with id {}", saved.getId());
        return subjectMapper.toResponse(saved);
    }

    @Override
    public void delete(UUID id) {
        Subject subject = findByOrThrow(id);
        subjectRepository.delete(subject);
        log.info("Subject deleted with id {}", id);
    }

    private void validateDuplicate(SubjectRequest request) {
        if (subjectRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Subject code already exists");
        }
    }

    private void validateDuplicate(UUID id, SubjectRequest request) {
        if (subjectRepository.existsByCodeAndIdNot(request.getCode(), id)) {
            throw new DuplicateResourceException("Subject code already exists");
        }
    }

    private Subject findByOrThrow(UUID id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + id));
    }

    private Department findDepartmentById(String departmentId) {
        UUID id;
        try {
            id = UUID.fromString(departmentId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Department id must be a valid UUID string");
        }

        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + departmentId));
    }
}
