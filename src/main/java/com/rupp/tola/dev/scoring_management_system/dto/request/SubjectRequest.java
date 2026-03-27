package com.rupp.tola.dev.scoring_management_system.dto.request;

import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectRequest {

    @NotBlank(message = "Subject Code is required.")
    @Size(max = 20, message = "Subject Code must be less than 20 characters.")
    private String subjectCode;

    @NotBlank(message = "Subject Name is required.")
    @Size(max = 100, message = "Subject Name must be less than 100 characters.")
    private String subjectName;

}
