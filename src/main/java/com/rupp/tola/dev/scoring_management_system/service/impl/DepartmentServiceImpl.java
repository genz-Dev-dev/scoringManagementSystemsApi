package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.constant.CodePrefix;
import com.rupp.tola.dev.scoring_management_system.dto.request.DepartmentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.DepartmentResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Department;
import com.rupp.tola.dev.scoring_management_system.exception.DuplicateResourceException;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.DepartmentMapper;
import com.rupp.tola.dev.scoring_management_system.repository.DepartmentRepository;
import com.rupp.tola.dev.scoring_management_system.service.DepartmentService;
import com.rupp.tola.dev.scoring_management_system.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    @Value("${uploads.department}")
    private String UPLOAD_DIRECTORY ;
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentResponse create(DepartmentRequest request) {
        validateDuplicate(request);

        Department department = departmentMapper.toEntity(request);
        if(request.getImage() != null && !request.getImage().isEmpty()) {
            String imageName = Util.uploadImage(request.getImage() , UPLOAD_DIRECTORY);
            department.setThumbnail(imageName);
        }
        Long departmentCode = departmentRepository.getNextDepartmentSequence();
        String code = CodePrefix.DEPARTMENT_CODE_PREFIX +  String.format("%04d", departmentCode);
        department.setCode(code);
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
        try {
            Department department = findByOrThrow(id);
            validateDuplicate(id, request);
            if(department.getThumbnail() != null) {
                Path thumbnailPath = Path.of(UPLOAD_DIRECTORY + department.getThumbnail());
                Files.deleteIfExists(thumbnailPath);
            }
            departmentMapper.updateFromRequest(request, department);
            if(request.getImage() != null && !request.getImage().isEmpty()) {
                String imageName = Util.uploadImage(request.getImage() , UPLOAD_DIRECTORY);
                department.setThumbnail(imageName);
            }
            Department saved = departmentRepository.save(department);
            log.info("Department updated with id {}", saved.getId());
            return departmentMapper.toResponse(saved);
        }catch (IOException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
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
    }

    private void validateDuplicate(UUID id, DepartmentRequest request) {
        if (departmentRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new DuplicateResourceException("Department name already exists");
        }
    }

    private Department findByOrThrow(UUID id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));
    }
}
