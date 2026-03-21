package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.entity.Student;
import com.rupp.tola.dev.scoring_management_system.service.impl.ExcelServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelServiceImplTest {

    private final ExcelServiceImpl excelService = new ExcelServiceImpl();

    @Test
    void testExportStudentsWithNumericCells() throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");


        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Kh First Name");

        Row row = sheet.createRow(1);
        row.createCell(1).setCellValue("Sok");
        row.createCell(2).setCellValue("San");
        row.createCell(3).setCellValue("Sok");
        row.createCell(4).setCellValue("San");
        row.createCell(5).setCellValue("M");
        row.createCell(6).setCellValue("2000-01-01");
        row.createCell(7).setCellValue("test@example.com");
        row.createCell(8).setCellValue(123456789);
        row.createCell(9).setCellValue(123);
        row.createCell(10).setCellValue("St 123");
        row.createCell(11).setCellValue("Sangkat 1");
        row.createCell(12).setCellValue("Khan 1");
        row.createCell(13).setCellValue("Phnom Penh");
        row.createCell(14).setCellValue("Cambodia");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        byte[] content = baos.toByteArray();

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "students.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                content
        );

        List<Student> students = excelService.exportStudents(file);

        assertEquals(1, students.size());
        Student student = students.get(0);
        assertEquals("Sok", student.getKhFirstName());
        assertEquals("123456789", student.getPhoneNumber());
        assertEquals("123", student.getAddress().getHouseNumber());
    }
}
