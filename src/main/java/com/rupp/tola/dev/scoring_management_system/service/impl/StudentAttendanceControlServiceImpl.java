package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.entity.StudentAttendanceControl;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.repository.*;
import com.rupp.tola.dev.scoring_management_system.service.StudentAttendanceControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentAttendanceControlServiceImpl implements StudentAttendanceControlService {
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final StudentAttendanceControlRepository studentAttendanceControlRepository;

    @Override
    public List<StudentAttendanceControl> create(List<StudentAttendanceControl> requests) {

        for (StudentAttendanceControl request : requests) {

            userRepository.findById(request.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("user not found..!"));

            studentRepository.findById(request.getStudent().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

            classRepository.findById(request.getClazz().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

            subjectRepository.findById(request.getSubject().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

            request.setIdDeleted(true);
            request.setCreateAt(LocalDate.now());
        }

        return studentAttendanceControlRepository.saveAll(requests);
    }

    @Override
    public List<StudentAttendanceControl> getAll() {
        return studentAttendanceControlRepository.findAll();
    }
}
