package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.service.ExcelService;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ExcerlServiceImpl implements ExcelService {
    @Override
    public List<StudentResponse> exportStudents(MultipartFile file) {
        Path path = Paths.get(file.getOriginalFilename());
        FileInputStream files = new FileInputStream(file);
        return List.of();
    }
}
