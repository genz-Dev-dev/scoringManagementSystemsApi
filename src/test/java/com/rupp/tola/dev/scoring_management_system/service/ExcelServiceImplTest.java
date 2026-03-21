package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.entity.Students;
import com.rupp.tola.dev.scoring_management_system.service.impl.ExcelServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelServiceImplTest {

    private final ExcelServiceImpl excelService = new ExcelServiceImpl();

    @Test
    void testExportStudentsWithNumericCells() throws IOException {
        // Create a workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");

        // Create header row (0)
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Kh First Name");
        // ... other headers

        // Create data row (1)
        Row row = sheet.createRow(1);
        row.createCell(1).setCellValue("Sok"); // Kh First Name
        row.createCell(2).setCellValue("San"); // Kh Last Name
        row.createCell(3).setCellValue("Sok"); // En First Name
        row.createCell(4).setCellValue("San"); // En Last Name
        row.createCell(5).setCellValue("M"); // Gender
        row.createCell(6).setCellValue("2000-01-01"); // DOB
        row.createCell(7).setCellValue("test@example.com"); // Email
        row.createCell(8).setCellValue(123456789); // Phone Number (NUMERIC)
        row.createCell(9).setCellValue(123); // House Number (NUMERIC)
        row.createCell(10).setCellValue("St 123"); // Street
        row.createCell(11).setCellValue("Sangkat 1"); // Sangkat
        row.createCell(12).setCellValue("Khan 1"); // Khan
        row.createCell(13).setCellValue("Phnom Penh"); // Province
        row.createCell(14).setCellValue("Cambodia"); // Country

        // Write to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        byte[] content = baos.toByteArray();

        // Create MockMultipartFile
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "students.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                content
        );

        // Call the service
        List<Students> students = excelService.exportStudents(file);

        // Verify
        assertEquals(1, students.size());
        Students student = students.get(0);
        assertEquals("Sok", student.getKhFirstName());
        assertEquals("123456789", student.getPhoneNumber()); // Check numeric conversion
        assertEquals("123", student.getAddress().getHouseNumber()); // Check numeric conversion
    }
}
