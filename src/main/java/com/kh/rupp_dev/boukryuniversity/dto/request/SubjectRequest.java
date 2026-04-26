package com.kh.rupp_dev.boukryuniversity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectRequest {

    @NotBlank(message = "Department id is required.")
    private String departmentId;

    private MultipartFile image;

    @NotBlank(message = "Subject name is required.")
    private String name;

    @NotBlank(message = "Subject description is required.")
    private String description;

}
