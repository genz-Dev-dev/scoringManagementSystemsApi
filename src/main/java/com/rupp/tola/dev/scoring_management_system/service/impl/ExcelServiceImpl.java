package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.entity.Address;
import com.rupp.tola.dev.scoring_management_system.entity.Students;
import com.rupp.tola.dev.scoring_management_system.exception.ExcelException;
import com.rupp.tola.dev.scoring_management_system.service.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    @Override
    public List<Students> exportStudents(MultipartFile file) {
        List<Students> students = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            for(Row row : sheet) {
                if(row.getRowNum() == 0) {
                    continue;
                }
                Students student = new Students();
                Address address = new Address();

                for (int i = 1; i <= 14; i++) {
                    Cell cell = row.getCell(i);
                    String cellValue = getCellValueAsString(cell);
                    
                    switch (i) {
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
                            if (cellValue != null && !cellValue.isEmpty()) {
                                try {
                                    student.setDateOfBirth(LocalDate.parse(cellValue));
                                } catch (Exception e) {
                                    log.warn("Failed to parse date: {}", cellValue);
                                    // Handle date parsing error or set to null/default
                                }
                            }
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
                student.setAddress(address);
                students.add(student);
            }
            log.info("Export students successfully: {}" , students.size());
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

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.format("%d", (long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                     return cell.getStringCellValue();
                } catch (IllegalStateException e) {
                     return String.valueOf(cell.getNumericCellValue());
                }
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}
