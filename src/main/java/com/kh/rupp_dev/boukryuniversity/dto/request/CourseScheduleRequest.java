package com.kh.rupp_dev.boukryuniversity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseScheduleRequest {

    @NotBlank(message = "Schedule dayOfWeek is required.")
    private String dayOfWeek;

    @NotBlank(message = "Schedule startTime is required.")
    private String startTime;

    @NotBlank(message = "Schedule endTime is required.")
    private String endTime;

    @NotNull(message = "Schedule room is required.")
    private Integer room;
}
