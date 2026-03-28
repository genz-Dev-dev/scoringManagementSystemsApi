package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

public interface ExcelService {

    List<Student> importStudents(MultipartFile file);

    ByteArrayInputStream exportStudent(UUID classId);

}
