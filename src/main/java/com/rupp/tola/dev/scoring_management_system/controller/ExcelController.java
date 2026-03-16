package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Students;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentsMapper;
import com.rupp.tola.dev.scoring_management_system.service.StudentService;
import com.rupp.tola.dev.scoring_management_system.service.excel.ExcelService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/excel")
@RequiredArgsConstructor
public class ExcelController {

    private final StudentsMapper studentsMapper;
    public ExcelService excelService;
    public StudentService studentService;

//    @GetMapping(path = "/exports")
//    public void exportStudentsToExcelFile(HttpServletResponse response) throws IOException {
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment; filename=students.xlsx");
//        List<Students> students = studentService.getStudents();
//        List<StudentResponse> dtos = studentsMapper.toResponseList(students);
//        excelService.exportToExcelFile(dtos, response.getOutputStream());
//    }

    @PostMapping(path = "/uploads")
    public ResponseEntity<?> uploadStudentToExcel(@RequestParam("files") MultipartFile files) {
        Map<Integer, String> upload = excelService.uploadToExcelFile(files);
        return ResponseEntity.ok(upload);
    }
}
