package com.rupp.tola.dev.scoring_management_system.dto.response;

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
public class CourseResponse {

    private UUID subjectId;

    private String subjectName;

    private UUID semesterId;
    private String semesterName;

    private UUID instructorId;
    private String instructorName;

    private String name;

    private String description;

    private String schedule;

    private List<CourseScheduleResponse> schedules;

    private String startAt;

    private String endAt;
}
