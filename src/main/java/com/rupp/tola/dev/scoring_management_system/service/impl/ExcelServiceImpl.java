package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.entity.Address;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import com.rupp.tola.dev.scoring_management_system.exception.ExcelException;
import com.rupp.tola.dev.scoring_management_system.service.ExcelService;
import com.rupp.tola.dev.scoring_management_system.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    @Override
    public List<Student> exportStudents(MultipartFile file) {
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
}
