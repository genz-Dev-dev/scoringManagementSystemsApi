package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.CourseRequest;
import com.rupp.tola.dev.scoring_management_system.dto.request.CourseScheduleRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.CourseResponse;
import com.rupp.tola.dev.scoring_management_system.dto.response.CourseScheduleResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Course;
import com.rupp.tola.dev.scoring_management_system.entity.CourseSchedule;
import com.rupp.tola.dev.scoring_management_system.entity.Semester;
import com.rupp.tola.dev.scoring_management_system.entity.Subject;
import com.rupp.tola.dev.scoring_management_system.entity.User;
import com.rupp.tola.dev.scoring_management_system.entity.composite.CourseId;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
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
        Semester semester = findSemesterById(request.getSemesterId());
        Subject subject = findSubjectById(request.getSubjectId());
        Course course = courseMapper.toEntity(request);
        User instructor = findInstructorById(request.getInstructorId());
        course.setInstructor(instructor);
        course.setStartAt(Util.convertToLocalDate(request.getStartAt()));
        course.setEndAt(Util.convertToLocalDate(request.getEndAt()));
        validateDateRange(course.getStartAt(), course.getEndAt());
        replaceSchedules(course, request.getSchedules());
        Course saved = courseRepository.save(course);
        log.info("Create course with request {}" , request);
        return toResponse(saved, semester.getName(), subject.getName());
    }

    @Override
    public CourseResponse update(UUID semesterId, UUID subjectId, CourseRequest request) {
        Course course = this.findByOrThrow(new CourseId(semesterId, subjectId));
        validateIdentityMatch(semesterId, subjectId, request);

        Semester semester = findSemesterById(request.getSemesterId());
        Subject subject = findSubjectById(request.getSubjectId());
        User instructor = findInstructorById(request.getInstructorId());

        courseMapper.updateFromRequest(request, course);
        course.setInstructor(instructor);
        course.setStartAt(Util.convertToLocalDate(request.getStartAt()));
        course.setEndAt(Util.convertToLocalDate(request.getEndAt()));
        validateDateRange(course.getStartAt(), course.getEndAt());
        replaceSchedules(course, request.getSchedules());

        Course saved = courseRepository.save(course);
        log.info("Updated course with semesterId {} and subjectId {}", semesterId, subjectId);
        return toResponse(saved, semester.getName(), subject.getName());
    }

    @Override
    public void delete(UUID semesterId, UUID subjectId) {
        Course course = this.findByOrThrow(new CourseId(semesterId, subjectId));
        log.info("Deleting course with semesterId {} and subjectId {}", semesterId, subjectId);
        courseRepository.delete(course);
    }

    @Override
    public Page<CourseResponse> getAll(Pageable pageable) {
        Page<Course> courses = courseRepository.findAll(pageable);
        return courses.map(this::toResponse);
    }

    @Override
    public CourseResponse getById(UUID semesterId, UUID subjectId) {
        Course course = this.findByOrThrow(new CourseId(semesterId, subjectId));
        log.info("Retrieving course with semesterId {} and subjectId {}", semesterId, subjectId);
        return toResponse(course);
    }

    private Course findByOrThrow(CourseId courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with semesterId " + courseId.getSemesterId() +
                                " and subjectId " + courseId.getSubjectId()
                ));
    }

    private CourseResponse toResponse(Course course , String semesterName , String subjectName) {
        CourseResponse response = courseMapper.toResponse(course);
        response.setSemesterName(semesterName);
        response.setSubjectName(subjectName);
        response.setSchedules(toScheduleResponses(course.getSchedules()));
        response.setSchedule(buildSchedule(course));
        return response;
    }

    private CourseResponse toResponse(Course course) {
        UUID semesterId = course.getCourseId().getSemesterId();
        UUID subjectId = course.getCourseId().getSubjectId();
        Semester semester = findSemesterById(semesterId);
        Subject subject = findSubjectById(subjectId);
        return toResponse(course, semester.getName(), subject.getName());
    }

    private Semester findSemesterById(UUID semesterId) {
        return semesterRepository.findById(semesterId)
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found with ID: " + semesterId));
    }

    private Subject findSubjectById(UUID subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + subjectId));
    }

    private User findInstructorById(UUID instructorId) {
        return userRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with ID: " + instructorId));
    }

    private void validateIdentityMatch(UUID semesterId, UUID subjectId, CourseRequest request) {
        if (!semesterId.equals(request.getSemesterId()) || !subjectId.equals(request.getSubjectId())) {
            throw new IllegalArgumentException("Course semesterId and subjectId in the request must match the URL.");
        }
    }

    private void validateDateRange(LocalDate startAt, LocalDate endAt) {
        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("Course start date must be before or equal to end date.");
        }
    }

    private void validateTimeRange(LocalTime startTime, LocalTime endTime) {
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Class start time must be before class end time.");
        }
    }

    private void replaceSchedules(Course course, List<CourseScheduleRequest> requests) {
        course.getSchedules().clear();
        for (CourseScheduleRequest request : requests) {
            CourseSchedule schedule = new CourseSchedule();
            schedule.setCourse(course);
            schedule.setDayOfWeek(parseDayOfWeek(request.getDayOfWeek()));
            schedule.setStartTime(parseTime(request.getStartTime(), "schedule.startTime"));
            schedule.setEndTime(parseTime(request.getEndTime(), "schedule.endTime"));
            schedule.setRoom(request.getRoom());
            validateTimeRange(schedule.getStartTime(), schedule.getEndTime());
            course.getSchedules().add(schedule);
        }
    }

    private DayOfWeek parseDayOfWeek(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("dayOfWeeks contains an empty value.");
        }

        String normalized = value.trim().toUpperCase(Locale.ROOT);
        return switch (normalized.substring(0, Math.min(normalized.length(), 3))) {
            case "MON" -> DayOfWeek.MONDAY;
            case "TUE" -> DayOfWeek.TUESDAY;
            case "WED" -> DayOfWeek.WEDNESDAY;
            case "THU" -> DayOfWeek.THURSDAY;
            case "FRI" -> DayOfWeek.FRIDAY;
            case "SAT" -> DayOfWeek.SATURDAY;
            case "SUN" -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException("Invalid dayOfWeek value: " + value);
        };
    }

    private LocalTime parseTime(String value, String fieldName) {
        try {
            return LocalTime.parse(value);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(fieldName + " must use HH:mm or HH:mm:ss format.");
        }
    }

    private List<CourseScheduleResponse> toScheduleResponses(List<CourseSchedule> schedules) {
        return schedules.stream()
                .sorted(scheduleComparator())
                .map(this::toScheduleResponse)
                .toList();
    }

    private CourseScheduleResponse toScheduleResponse(CourseSchedule schedule) {
        return CourseScheduleResponse.builder()
                .id(schedule.getId())
                .dayOfWeek(formatDayOfWeek(schedule.getDayOfWeek()))
                .startTime(formatTime(schedule.getStartTime()))
                .endTime(formatTime(schedule.getEndTime()))
                .room(schedule.getRoom())
                .build();
    }

    private String formatDayOfWeek(DayOfWeek dayOfWeek) {
        String name = dayOfWeek.name().toLowerCase(Locale.ROOT);
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    private String formatTime(LocalTime time) {
        return time == null ? null : time.toString();
    }

    private String buildSchedule(Course course) {
        return course.getSchedules().stream()
                .sorted(scheduleComparator())
                .map(schedule -> formatDayOfWeek(schedule.getDayOfWeek()) + " "
                        + formatTime(schedule.getStartTime()) + "-" + formatTime(schedule.getEndTime())
                        + " room " + schedule.getRoom())
                .reduce((left, right) -> left + "; " + right)
                .orElse("");
    }

    private Comparator<CourseSchedule> scheduleComparator() {
        return Comparator
                .comparing((CourseSchedule schedule) -> schedule.getDayOfWeek().getValue())
                .thenComparing(CourseSchedule::getStartTime)
                .thenComparing(CourseSchedule::getEndTime);
    }

}
