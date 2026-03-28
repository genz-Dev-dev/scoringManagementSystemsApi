package com.rupp.tola.dev.scoring_management_system.controller;

import com.rupp.tola.dev.scoring_management_system.data.SingleResponse;
import com.rupp.tola.dev.scoring_management_system.dto.request.CourseRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.CourseResponse;
import com.rupp.tola.dev.scoring_management_system.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<SingleResponse<List<CourseResponse>>> getAll() {
        List<CourseResponse> response = courseService.getAll();
        return ResponseEntity.ok(SingleResponse.success("Successfully to retrieve all course data." , response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<CourseResponse>> getById(@PathVariable UUID id) {
        CourseResponse courseResponse = courseService.getById(id);
        return ResponseEntity.ok(SingleResponse.success("Successfully to retrieve course data." , courseResponse));
    }

    @PostMapping
    public ResponseEntity<SingleResponse<CourseResponse>> create(@Valid @RequestBody CourseRequest courseRequest) {
        CourseResponse response = courseService.create(courseRequest);
        return ResponseEntity.ok(SingleResponse.success("Successfully to create course data." , response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SingleResponse<CourseResponse>> update(@PathVariable UUID id, @RequestBody CourseRequest courseRequest) {
        return ResponseEntity.ok(SingleResponse.success("Successfully to update course data." , courseService.update(id,courseRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SingleResponse<Void>> delete(@PathVariable UUID id) {
        courseService.delete(id);
        return ResponseEntity.ok(SingleResponse.success("Successfully to delete course data." , null));
    }

}
