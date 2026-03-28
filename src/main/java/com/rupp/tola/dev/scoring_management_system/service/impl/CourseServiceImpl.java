package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.CourseRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.CourseResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Course;
import com.rupp.tola.dev.scoring_management_system.entity.Semester;
import com.rupp.tola.dev.scoring_management_system.entity.Subject;
import com.rupp.tola.dev.scoring_management_system.entity.User;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.CourseMapper;
import com.rupp.tola.dev.scoring_management_system.repository.CourseRepository;
import com.rupp.tola.dev.scoring_management_system.repository.SemesterRepository;
import com.rupp.tola.dev.scoring_management_system.repository.SubjectRepository;
import com.rupp.tola.dev.scoring_management_system.repository.UserRepository;
import com.rupp.tola.dev.scoring_management_system.service.CourseService;
import com.rupp.tola.dev.scoring_management_system.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseResponse create(CourseRequest request) {
        Course course = courseMapper.toEntity(request);
        CourseResponse response = courseMapper.toResponse(course);
        checkIsSemesterAndSubjectExists(request.getSemesterId() , request.getSubjectId() , response);
        User instructor = userRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with ID: " + request.getInstructorId()));
        course.setInstructor(instructor);
        course.setSchedule(Util.convertToLocalDate(request.getSchedule()));
        course.setStartAt(Util.convertToLocalDate(request.getStartAt()));
        course.setEndAt(Util.convertToLocalDate(request.getEndAt()));
        Course saved = courseRepository.save(course);
        log.info("Create course with request {}" , request);
        return response;
    }

    @Override
    public CourseResponse update(UUID uuid, CourseRequest request) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {
        Course course = this.findByOrThrow(uuid);
        log.info("Deleting course with id {}", uuid);
        courseRepository.delete(course);
    }

    @Override
    public List<CourseResponse> getAll() {
        List<Course> courses = courseRepository.findAll();
        log.info("Retrieving all courses {}", courses);
        return courseMapper.toList(courses);
    }

    @Override
    public CourseResponse getById(UUID uuid) {
        Course course = this.findByOrThrow(uuid);
        log.info("Retrieving course with id {}", uuid);
        return courseMapper.toResponse(course);
    }

    private Course findByOrThrow(UUID uuid) {
        return courseRepository.findById(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("Course not found with id " + uuid));
    }


    private void checkIsSemesterAndSubjectExists(UUID semesterId, UUID subjectId , CourseResponse response) {
        Optional<Semester> semester = semesterRepository.findById(semesterId);
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if(semester.isEmpty() || subject.isEmpty()) {
            throw new  ResourceNotFoundException("Semester or Subject not found");
        }
        response.setSemesterName(semester.get().getName());
        response.setSubjectName(subject.get().getName());
    }

}
