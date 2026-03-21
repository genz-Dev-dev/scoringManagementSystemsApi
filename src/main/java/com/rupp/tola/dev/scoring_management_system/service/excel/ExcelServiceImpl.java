package com.rupp.tola.dev.scoring_management_system.service.excel;

import com.rupp.tola.dev.scoring_management_system.entity.Students;
import com.rupp.tola.dev.scoring_management_system.util.ExcelFileExporter;
import com.rupp.tola.dev.scoring_management_system.util.StudentCodeGenerateUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Deprecated
public class ExcelServiceImpl implements ExcelService{

    @Override
    public Map<Integer, String> uploadToExcelFile(MultipartFile file) {
        Map<Integer, String> map = new HashedMap<>();

        if (file == null || file.isEmpty()) {
            map.put(0, "File is empty");
            return map;
        }

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.createSheet("Students");
            Sheet sheetIndex = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            for (int i = 1; i <= sheetIndex.getLastRowNum(); i++) {
                Row row = sheetIndex.getRow(i);
                int rowNumber = i++;
                if (row == null || isRowEmpty(row)) {
                    map.put(rowNumber, "Row is empty.");
                    continue;
                }

                List<String> rowErrors = new ArrayList<>();

                String noValue = dataFormatter.formatCellValue(row.getCell(0)).trim();
                String studentCodeValue = dataFormatter.formatCellValue(row.getCell(1)).trim();
                String subjectCodeValue = dataFormatter.formatCellValue(row.getCell(2)).trim();
                String semesterValue = dataFormatter.formatCellValue(row.getCell(3)).trim();
                String scoreValue = dataFormatter.formatCellValue(row.getCell(4)).trim();

                if (noValue.isBlank()) {
                    rowErrors.add("No is required");
                } else {
                    try {
                        Integer.valueOf(noValue);
                    } catch (NumberFormatException e) {
                        rowErrors.add("No must be an integer");
                    }
                }

                if (studentCodeValue.isBlank()) {
                    studentCodeValue = StudentCodeGenerateUtils.generator();
                } else if (studentCodeValue.length() > 20) {
                    rowErrors.add("Student code must not exceed 20 characters");
                }

                if (subjectCodeValue.isBlank()) {
                    rowErrors.add("Subject code is required");
                } else if (subjectCodeValue.length() > 10) {
                    rowErrors.add("Subject code must not exceed 10 characters");
                }

                if (semesterValue.isBlank()) {
                    rowErrors.add("Semester is required");
                } else if (semesterValue.length() > 20) {
                    rowErrors.add("Semester must not exceed 20 characters");
                }

                if (scoreValue.isBlank()) {
                    rowErrors.add("Score is required");
                } else {
                    try {
                        double score = Double.parseDouble(scoreValue);
                        if (score < 0 || score > 100) {
                            rowErrors.add("Score must be between 0 and 100");
                        }
                    } catch (NumberFormatException e) {
                        rowErrors.add("Score must be numeric");
                    }
                }

                double score = Double.parseDouble(scoreValue);

//			Students student = new Students();
//			student.setStudentCode(studentCodeValue);
//			studentsRepository.save(student);

            }

        } catch (IOException ex) {
            ex.getStackTrace();
        }
        return map;
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                if (!new DataFormatter().formatCellValue(cell).trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void exportToExcelFile(HttpServletResponse response, List<Students> studentsList)throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=students.xlsx");
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Students");
            CellStyle headerStyle = ExcelFileExporter.createHeaderStyle(workbook);
            CellStyle bodyStyle = ExcelFileExporter.createBodyStyle(workbook);

            int rowIndex = 0;

            Row header = sheet.createRow(rowIndex++);

            Cell field = header.createCell(0);
            field.setCellValue("ID");
            field.setCellStyle(headerStyle);

            Cell field2 = header.createCell(1);
            field2.setCellValue("Code");
            field2.setCellStyle(headerStyle);

            Cell field3 = header.createCell(2);
            field3.setCellValue("Name");
            field3.setCellStyle(headerStyle);

            Cell field4 = header.createCell(3);
            field4.setCellValue("Status");
            field4.setCellStyle(headerStyle);

            for (Students student : studentsList) {

                Row row = sheet.createRow(rowIndex++);

                Cell c1 = row.createCell(0);
                c1.setCellValue(student.getId().toString());
                c1.setCellStyle(bodyStyle);

//                Cell c2 = row.createCell(1);
//                c2.setCellValue(student.getStudentCode());
//                c2.setCellStyle(bodyStyle);
//
//                Cell c3 = row.createCell(2);
//                c3.setCellValue(student.getFirstname());
//                c3.setCellStyle(bodyStyle);
//
//                Cell c4 = row.createCell(3);
//                c4.setCellValue(student.getLastName());
//                c4.setCellStyle(bodyStyle);
//
//                Cell c5 = row.createCell(4);
//                c5.setCellValue(student.getStatus());
//                c5.setCellStyle(bodyStyle);
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);

            try (ServletOutputStream outputStream = response.getOutputStream()) {
                workbook.write(outputStream);
            }catch (IOException ex) {
                ex.getMessage();
            }

        } catch (IOException ex) {
            ex.getStackTrace();
        }

    }
}
