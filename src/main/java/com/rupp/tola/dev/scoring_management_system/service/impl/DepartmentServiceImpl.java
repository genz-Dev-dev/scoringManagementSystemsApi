package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.DepartmentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.DepartmentResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Department;
import com.rupp.tola.dev.scoring_management_system.exception.DuplicateResourceException;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.DepartmentMapper;
import com.rupp.tola.dev.scoring_management_system.repository.DepartmentRepository;
import com.rupp.tola.dev.scoring_management_system.service.DepartmentService;
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
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentResponse create(DepartmentRequest request) {
        validateDuplicate(request);

        Department department = departmentMapper.toEntity(request);
        Department saved = departmentRepository.save(department);
        log.info("Department created with id {}", saved.getId());
        return departmentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAll() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getById(UUID id) {
        Department department = findByOrThrow(id);
        log.info("Department found with id {}", id);
        return departmentMapper.toResponse(department);
    }

    @Override
    public DepartmentResponse update(UUID id, DepartmentRequest request) {
        Department department = findByOrThrow(id);
        validateDuplicate(id, request);

        departmentMapper.updateFromRequest(request, department);
        Department saved = departmentRepository.save(department);
        log.info("Department updated with id {}", saved.getId());
        return departmentMapper.toResponse(saved);
    }

    @Override
    public void delete(UUID id) {
        Department department = findByOrThrow(id);
        departmentRepository.delete(department);
        log.info("Department deleted with id {}", id);
    }

    private void validateDuplicate(DepartmentRequest request) {
        if (departmentRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Department name already exists");
        }

        if (departmentRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Department code already exists");
        }
    }

    private void validateDuplicate(UUID id, DepartmentRequest request) {
        if (departmentRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new DuplicateResourceException("Department name already exists");
        }

        if (departmentRepository.existsByCodeAndIdNot(request.getCode(), id)) {
            throw new DuplicateResourceException("Department code already exists");
        }
    }

    private Department findByOrThrow(UUID id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));
    }
}
