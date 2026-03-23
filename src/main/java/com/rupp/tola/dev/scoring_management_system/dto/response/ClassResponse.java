package com.rupp.tola.dev.scoring_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassResponse {
    
    private UUID id;
    
    private String name;
    
    private Boolean status;
    
    @JsonProperty("students")
    private List<StudentResponse> students;
    
    private LocalDateTime creationAt;
    
    private LocalDateTime updatedAt;
}
