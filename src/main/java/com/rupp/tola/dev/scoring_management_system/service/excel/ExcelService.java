package com.rupp.tola.dev.scoring_management_system.service.excel;

import com.rupp.tola.dev.scoring_management_system.entity.Student;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Deprecated
public interface ExcelService {

    Map<Integer, String> uploadToExcelFile(MultipartFile file);

    void exportToExcelFile(HttpServletResponse response, List<Student> studentList)throws IOException;

}
