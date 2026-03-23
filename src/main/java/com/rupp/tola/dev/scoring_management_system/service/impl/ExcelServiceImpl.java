package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.entity.Address;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import com.rupp.tola.dev.scoring_management_system.exception.ExcelException;
import com.rupp.tola.dev.scoring_management_system.repository.StudentRepository;
import com.rupp.tola.dev.scoring_management_system.service.ExcelService;
import com.rupp.tola.dev.scoring_management_system.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    private final StudentRepository studentRepository;

    @Override
    public List<Student> importStudents(MultipartFile file) {
        List<Student> students = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            for(Row row : sheet) {
                if(row.getRowNum() == 0) {
                    continue;
                }
                Student student = new Student();
                Address address = new Address();

                for (int i = 1; i <= 14; i++) {
                    Cell cell = row.getCell(i);
                    String cellValue = Util.getCellValueAsString(cell);
                    if(cellValue.isBlank()) continue;
                    excelStudentMapping(i , student , address , cellValue);
                }
                
                student.setAddress(address);
                students.add(student);
            }
            log.info("Export students successfully: {}" , students.size());
            workbook.close();
            return students;
        }catch (IOException ex) {
            log.error("Could not open Excel file.", ex);
            throw new ExcelException("Could not open Excel file.");
        }
        catch (ExcelException ex) {
            log.error("Error while reading Excel file.", ex);
            throw new ExcelException("Error while reading Excel file.");
        }
    }

    @Override
    public ByteArrayInputStream exportStudent() {
        try {
            List<Student> students = studentRepository.findAll();

            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = headerStudentExcel(workbook);

            int rowIndex = 1;
            for (Student student : students) {
                Row row = sheet.createRow(rowIndex++);
                writeStudentToRow(row, student);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
                workbook.write(out);

            workbook.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException ex) {
            log.info("Could not open Excel file.", ex);
            throw new ExcelException("Could not open Excel file.");
        }
    }

    /**
     * mapping data for create cell or column for each row
     * @param row
     * @param student
     */
    private void writeStudentToRow(Row row, Student student) {
        Address address = student.getAddress();

        row.createCell(0).setCellValue(student.getId().toString());
        row.createCell(1).setCellValue(Util.text(student.getKhFirstName()));
        row.createCell(2).setCellValue(Util.text(student.getKhLastName()));
        row.createCell(3).setCellValue(Util.text(student.getEnFirstName()));
        row.createCell(4).setCellValue(Util.text(student.getEnLastName()));
        row.createCell(5).setCellValue(Util.text(student.getGender()));
        if (student.getDateOfBirth() != null) {
            row.createCell(6).setCellValue(student.getDateOfBirth().toString());
        } else {
            row.createCell(6).setCellValue("");
        }
        row.createCell(7).setCellValue(Util.text(student.getEmail()));
        row.createCell(8).setCellValue(Util.text(student.getPhoneNumber()));

        if (address != null) {
            row.createCell(9).setCellValue(Util.text(address.getHouseNumber()));
            row.createCell(10).setCellValue(Util.text(address.getStreet()));
            row.createCell(11).setCellValue(Util.text(address.getSangkat()));
            row.createCell(12).setCellValue(Util.text(address.getKhan()));
            row.createCell(13).setCellValue(Util.text(address.getProvince()));
            row.createCell(14).setCellValue(Util.text(address.getCountry()));
        } else {
            for (int c = 9; c <= 14; c++) {
                row.createCell(c).setCellValue("");
            }
        }
    }

    private Sheet headerStudentExcel(Workbook workbook) {
        Sheet sheet = workbook.createSheet("students");

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillBackgroundColor(IndexedColors.GREEN.getIndex());

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setFontName("Inter");
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);

        String[] titles = {
                "ID",
                "នាមខ្លួន",
                "នាមត្រកូល",
                "First Name",
                "Last Name",
                "Gender",
                "Date of Birth",
                "Email",
                "Phone Number",
                "House No",
                "Street",
                "Sangkat",
                "Khan",
                "Province",
                "Country"
        };
        for (int i = 0; i < titles.length; i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(titles[i]);
            headerCell.setCellStyle(headerStyle);
        }
        return sheet;
    }

    private void excelStudentMapping(int index , Student student, Address address, String cellValue) {
        switch (index) {
            case 1:
                student.setKhFirstName(cellValue);
                break;
            case 2:
                student.setKhLastName(cellValue);
                break;
            case 3:
                student.setEnFirstName(cellValue);
                break;
            case 4:
                student.setEnLastName(cellValue);
                break;
            case 5:
                student.setGender(cellValue);
                break;
            case 6:
                student.setDateOfBirth(Util.convertToLocalDate(cellValue));
                break;
            case 7:
                student.setEmail(cellValue);
                break;
            case 8:
                student.setPhoneNumber(cellValue);
                break;
            case 9:
                address.setHouseNumber(cellValue);
                break;
            case 10:
                address.setStreet(cellValue);
                break;
            case 11:
                address.setSangkat(cellValue);
                break;
            case 12:
                address.setKhan(cellValue);
                break;
            case 13:
                address.setProvince(cellValue);
                break;
            case 14:
                address.setCountry(cellValue);
                break;
            default:
                break;
        }
    }
}
