package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.dto.request.CourseRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.CourseResponse;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    CourseResponse create(CourseRequest request);

    CourseResponse update(UUID uuid, CourseRequest request);

    void delete(UUID uuid);

    List<CourseResponse> getAll();

    CourseResponse getById(UUID uuid);
}
