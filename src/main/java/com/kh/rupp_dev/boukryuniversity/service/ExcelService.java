package com.kh.rupp_dev.boukryuniversity.service;

import com.kh.rupp_dev.boukryuniversity.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

public interface ExcelService {

    List<Student> importStudents(MultipartFile file);

    ByteArrayInputStream exportStudent(UUID classId);

}
