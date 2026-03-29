package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.ScoreRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.ScoreResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Course;
import com.rupp.tola.dev.scoring_management_system.entity.Score;
import com.rupp.tola.dev.scoring_management_system.entity.composite.CourseId;
import com.rupp.tola.dev.scoring_management_system.exception.ResourceNotFoundException;
import com.rupp.tola.dev.scoring_management_system.mapper.ScoreMapper;
import com.rupp.tola.dev.scoring_management_system.repository.CourseRepository;
import com.rupp.tola.dev.scoring_management_system.repository.ScoreRepository;
import com.rupp.tola.dev.scoring_management_system.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;
    private final ScoreMapper scoreMapper;
    private final CourseRepository courseRepository;

    @Override
    public ScoreResponse create(ScoreRequest request) {
        CourseId courseId = new CourseId(request.getSemesterId(), request.getSubjectId());
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        Score score = scoreMapper.toEntity(request);
        score.setCourse(course);
        return scoreMapper.toResponse(scoreRepository.save(score));
    }

    @Override
    public ScoreResponse update(UUID id, ScoreRequest request) {
        Score score = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found with id: " + id));

        CourseId courseId = new CourseId(request.getSemesterId(), request.getSubjectId());
        CourseId oldCourseId = score.getCourse().getCourseId();

        if (!courseId.equals(oldCourseId)) {
            Course newCourse = courseRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
            score.setCourse(newCourse);
        }

        scoreMapper.updateFromRequest(request, score);

        log.info("Updated score id: {}", id);
        return scoreMapper.toResponse(scoreRepository.save(score));
    }

    @Override
    public ScoreResponse getById(UUID id) {
        Score score = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject Not Found: " + id));
        log.info("Getting score with id: {}", id);
        return scoreMapper.toResponse(score);
    }

    @Override
    public List<ScoreResponse> getAll() {
        List<Score> scores = scoreRepository.findAll();
        log.info("Score was got collected successfully.");
        return scores.stream()
                .map(scoreMapper::toResponse)
                .toList();
    }

    @Override
    public List<ScoreResponse> findByStudentId(UUID studentId) {
//        log.info("Student was found with id {}", studentId);
//        return scoreMapper.toResponseList(
//                scoreRepository.findByStudentId(studentId)
//        );
        return null;
    }

    @Override
    public List<ScoreResponse> findByCourse(UUID semesterId, UUID subjectId) {
//        CourseId courseId = new CourseId(semesterId, subjectId);
//        log.info("Course was found with id {} from semester {}", courseId, semesterId );
//        return scoreMapper.toResponseList(
//                scoreRepository.findByCourseId(courseId)
//        );
        return null;
    }

    @Override
    public void delete(UUID id) {
        Score score = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject Not Found: " + id));
        log.info("Deleting score with id: {}", id);
        scoreRepository.delete(score);
    }

}