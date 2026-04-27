package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import com.rupp.tola.dev.scoring_management_system.entity.*;
import com.rupp.tola.dev.scoring_management_system.entity.composite.CourseId;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.ScoreMapper;
import com.rupp.tola.dev.scoring_management_system.repository.*;
import com.rupp.tola.dev.scoring_management_system.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final SemesterRepository semesterRepository;
    private final ScoreMapper scoreMapper;
    private final CourseRepository courseRepository;

    @Override
    public List<ScoreResponse> create(List<ScoreRequest> requests) {

        List<Score> scores = requests.stream()
                .map(this::mapToEntity)
                .toList();

        List<Score> saved = scoreRepository.saveAll(scores);

        return saved.stream()
                .map(scoreMapper::toResponse)
                .toList();
    }

    private Score mapToEntity(ScoreRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Semester semester = semesterRepository.findById(request.getSemesterId())
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Score score = new Score();
        score.setStudent(student);
        score.setSubject(subject);
        score.setSemester(semester);
        score.setUser(user);
        score.setScore(BigDecimal.valueOf(request.getScore()));
        score.setVersion(request.getVersion());
        score.setStatus(request.isStatus());

        return score;
    }

    @Override
    public ScoreResponse update(UUID id, ScoreRequest request) {
        return null;
    }

    @Override
    public ScoreResponse getById(UUID id) {
        return null;
    }

    @Override
    public List<ScoreResponse> getAll() {
        return List.of();
    }

    @Override
    public void delete(UUID id) {

    }
//    @Override
//    public ScoreResponse update(UUID id, ScoreRequest request) {
//        Score score = scoreRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Score not found with id: " + id));
//
//        CourseId courseId = new CourseId(request.getSemesterId().getId(), request.getSubjectId().getId());
//        CourseId oldCourseId = score.getSubject().getId();
//
//        if (!courseId.equals(oldCourseId)) {
//            Course newCourse = courseRepository.findById(courseId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
//            score.setCourse(newCourse);
//        }
//
////        scoreMapper.updateFromRequest(request, score);
//
//        log.info("Updated score id: {}", id);
//        return scoreMapper.toResponse(scoreRepository.save(score));
//    }

//    @Override
//    public ScoreResponse getById(UUID id) {
//        Score score = scoreRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Subject Not Found: " + id));
//        log.info("Getting score with id: {}", id);
//        return scoreMapper.toResponse(score);
//    }
//
//    @Override
//    public List<ScoreResponse> getAll() {
//        List<Score> scores = scoreRepository.findAll();
//        log.info("Score was got collected successfully.");
//        return scores.stream()
//                .map(scoreMapper::toResponse)
//                .toList();
//    }
//
//    @Override
//    public List<ScoreResponse> findByStudentId(UUID studentId) {
////        log.info("Student was found with id {}", studentId);
////        return scoreMapper.toResponseList(
////                scoreRepository.findByStudentId(studentId)
////        );
//        return null;
//    }
//
//    @Override
//    public List<ScoreResponse> findByCourse(UUID semesterId, UUID subjectId) {
////        CourseId courseId = new CourseId(semesterId, subjectId);
////        log.info("Course was found with id {} from semester {}", courseId, semesterId );
////        return scoreMapper.toResponseList(
////                scoreRepository.findByCourseId(courseId)
////        );
//        return null;
//    }

//    @Override
//    public void delete(UUID id) {
//        Score score = scoreRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Subject Not Found: " + id));
//        log.info("Deleting score with id: {}", id);
//        scoreRepository.delete(score);
//    }

}