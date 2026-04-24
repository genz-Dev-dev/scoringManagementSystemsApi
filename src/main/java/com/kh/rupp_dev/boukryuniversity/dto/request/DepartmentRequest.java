package com.kh.rupp_dev.boukryuniversity.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentRequest {

    @NotBlank(message = "Department name is required.")
    private String name;

    @JsonProperty("thumbnail")
    private MultipartFile thumbnail;

    @NotBlank(message = "Department description is required.")
    private String description;
}
