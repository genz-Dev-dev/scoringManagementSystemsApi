package com.kh.rupp_dev.boukryuniversity.service;

import com.kh.rupp_dev.boukryuniversity.dto.request.CourseRequest;
import com.kh.rupp_dev.boukryuniversity.dto.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseService {

    CourseResponse create(CourseRequest request);

    CourseResponse update(UUID semesterId, UUID subjectId, CourseRequest request);

    void delete(UUID semesterId, UUID subjectId);

    Page<CourseResponse> getAll(Pageable pageable);

    CourseResponse getById(UUID semesterId, UUID subjectId);
}
