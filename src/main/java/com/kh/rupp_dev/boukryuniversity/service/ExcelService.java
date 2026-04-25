package com.kh.rupp_dev.boukryuniversity.service;

import com.kh.rupp_dev.boukryuniversity.dto.request.UploadBatchesRequest;
import com.kh.rupp_dev.boukryuniversity.entity.Student;
import com.kh.rupp_dev.boukryuniversity.entity.UploadBatches;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ExcelService {

    List<Student> importStudents(MultipartFile file , UploadBatches request) throws IOException;

    ByteArrayInputStream exportStudent(UUID classId);

}
