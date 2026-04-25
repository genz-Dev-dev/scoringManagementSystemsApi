package com.kh.rupp_dev.boukryuniversity.service.impl;

import com.kh.rupp_dev.boukryuniversity.constant.CodePrefix;
import com.kh.rupp_dev.boukryuniversity.dto.request.DepartmentRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.DepartmentResponse;
import com.kh.rupp_dev.boukryuniversity.entity.Department;
import com.kh.rupp_dev.boukryuniversity.exception.DuplicateResourceException;
import com.kh.rupp_dev.boukryuniversity.exception.ResourceNotFoundException;
import com.kh.rupp_dev.boukryuniversity.mapper.DepartmentMapper;
import com.kh.rupp_dev.boukryuniversity.repository.DepartmentRepository;
import com.kh.rupp_dev.boukryuniversity.service.DepartmentService;
import com.kh.rupp_dev.boukryuniversity.utils.Util;
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
        if(request.getThumbnail() != null && !request.getThumbnail().isEmpty()) {
            String imageName = Util.uploadImage(request.getThumbnail() , UPLOAD_DIRECTORY);
            department.setThumbnail(imageName);
        }
        Long departmentCode = departmentRepository.getNextDepartmentSequence();
        String code = String.format("%s%04d" , CodePrefix.DEPARTMENT_CODE_PREFIX , departmentCode);
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
                Path thumbnailPath = Path.of(UPLOAD_DIRECTORY).resolve(department.getThumbnail());
                Files.deleteIfExists(thumbnailPath);
            }
            departmentMapper.updateFromRequest(request, department);
            if(request.getThumbnail() != null && !request.getThumbnail().isEmpty()) {
                String imageName = Util.uploadImage(request.getThumbnail() , UPLOAD_DIRECTORY);
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
