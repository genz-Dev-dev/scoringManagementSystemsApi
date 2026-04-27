package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.constant.CodePrefix;
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
import com.rupp.tola.dev.scoring_management_system.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    @Value("${uploads.subject}")
    private String UPLOAD_DIRECTORY;
    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public SubjectResponse create(SubjectRequest request) {

        Subject subject = subjectMapper.toEntity(request);
        if(request.getImage() != null && !request.getImage().isEmpty()){
            String imageName = Util.uploadImage(request.getImage() , UPLOAD_DIRECTORY);
            subject.setThumbnail(imageName);
        }
        subject.setDepartment(findDepartmentById(request.getDepartmentId()));
        Long subjectNumber = subjectRepository.getNextSequenceSubject();
        String code = CodePrefix.SUBJECT_CODE_PREFIX + String.format("%04d" , subjectNumber);
        subject.setCode(code);
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
        try {
            Subject subject = findByOrThrow(id);
            if (subject.getThumbnail() != null) {
                Path thumbnailPath = Path.of(UPLOAD_DIRECTORY).resolve(subject.getThumbnail());
                Files.deleteIfExists(thumbnailPath);
            }
            subjectMapper.updateFromRequest(request, subject);
            if(request.getImage() != null && !request.getImage().isEmpty()){
                String imageName = Util.uploadImage(request.getImage() , UPLOAD_DIRECTORY);
                subject.setThumbnail(imageName);
            }
            subject.setDepartment(findDepartmentById(request.getDepartmentId()));

            Subject saved = subjectRepository.save(subject);
            log.info("Subject updated with id {}", saved.getId());
            return subjectMapper.toResponse(saved);
        }catch (IOException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public void delete(UUID id) {
        Subject subject = findByOrThrow(id);
        subjectRepository.delete(subject);
        log.info("Subject deleted with id {}", id);
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
