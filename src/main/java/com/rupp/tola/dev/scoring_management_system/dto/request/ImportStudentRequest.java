package com.rupp.tola.dev.scoring_management_system.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportStudentRequest {

    private String classId;
    private MultipartFile file;

}
