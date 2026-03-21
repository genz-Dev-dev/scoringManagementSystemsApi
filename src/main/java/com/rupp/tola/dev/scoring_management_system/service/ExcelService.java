package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelService {
    List<Student> exportStudents(MultipartFile file);
}
