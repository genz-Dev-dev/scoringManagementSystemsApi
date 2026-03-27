package com.rupp.tola.dev.scoring_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRequest {

    @NotNull(message = "Subject ID is required.")
    private UUID subjectId;

    @NotNull(message = "Semester ID is required.")
    private UUID semesterId;

    @NotNull(message = "Instructor ID is required.")
    private UUID instructorId;

    @NotBlank(message = "Name is required.")
    @Size(min = 2 , max = 50 , message = "Name must be between 2 and 50 characters.")
    private String name;

    @NotBlank(message = "Description is required.")
    @Size(min = 5 , max = 100 , message = "Description must be between 5 and 100 characters.")
    private String description;

    @NotBlank(message = "Schedule is required.")
    private String schedule;

    @NotNull(message = "Room is required.")
    private int room;

    @NotBlank(message = "Start at is required.")
    private String startAt;

    @NotBlank(message = "End at is required.")
    private String endAt;
}
