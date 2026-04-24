package com.kh.rupp_dev.boukryuniversity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseScheduleResponse {

    private UUID id;

    private String dayOfWeek;

    private String startTime;

    private String endTime;

    private Integer room;
}
