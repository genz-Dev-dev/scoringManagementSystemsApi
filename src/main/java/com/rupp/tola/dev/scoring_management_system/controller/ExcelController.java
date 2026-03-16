package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.entity.Students;
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

    public ExcelService excelService;
    public StudentService studentService;

//    @GetMapping(path = "/exports")
//    public ResponseEntity<?> exportStudentsToExcelFile(HttpServletResponse response) throws IOException {
//        List<Students> listOfStudents = studentService.getStudents();
//        return ResponseEntity.ok(listOfStudents);
//    }

    @PostMapping(path = "/uploads")
    public ResponseEntity<?> uploadStudentToExcel(@RequestParam("files") MultipartFile files) {
        Map<Integer, String> upload = excelService.uploadToExcelFile(files);
        return ResponseEntity.ok(upload);
    }
}
