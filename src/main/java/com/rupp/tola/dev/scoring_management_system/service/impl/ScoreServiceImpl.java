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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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

        if (scoreRepository.existsByStudent_IdAndCourse_CourseId(request.getStudentId(), courseId)) {
            throw new RuntimeException("Score already exists");
        }

        Score score = scoreMapper.toEntity(request);
        score.setCourse(course);

        return scoreMapper.toResponse(scoreRepository.save(score));
    }

    @Override
    public List<ScoreResponse> createBulk(List<ScoreRequest> requests) {
        List<Score> scores = requests.stream()
                .map(request -> {

                    CourseId courseId = new CourseId(
                            request.getSemesterId(),
                            request.getSubjectId()
                    );

                    Course course = courseRepository.findById(courseId)
                            .orElseThrow(() -> new ResourceNotFoundException("Course not found."));

                    if (scoreRepository.existsByStudent_IdAndCourse_CourseId(request.getStudentId(), courseId)){
                        throw new RuntimeException("Score already exits for students: " + request.getStudentId());
                    }

                    Score score = scoreMapper.toEntity(request);
                    score.setCourse(course);

                    return score;
                }).toList();

        return scoreRepository.saveAll(scores).stream()
                .map(scoreMapper::toResponse)
                .toList();
    }

    @Override
    public ScoreResponse updateScoreValue(UUID id, ScoreRequest request) {
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
    public Page<ScoreResponse> getAll(org.springframework.data.domain.Pageable pageable) {
        return scoreRepository.findAll(pageable)
                .map(scoreMapper::toResponse);
    }

    @Override
    public List<ScoreResponse> findByStudentId(UUID studentId) {
        return scoreRepository.findByStudent_Id(studentId)
                .stream()
                .map(scoreMapper::toResponse)
                .toList();
    }

    @Override
    public List<ScoreResponse> findByCourse(UUID semesterId, UUID subjectId) {
        CourseId courseId = new CourseId(semesterId, subjectId);
        return scoreRepository.findByCourse_CourseId(courseId)
                .stream()
                .map(scoreMapper::toResponse)
                .toList();
    }

    @Override
    public ScoreResponse findByStudentAndCourse(UUID studentId, UUID semesterId, UUID subjectId) {
        CourseId courseId = new CourseId(semesterId, subjectId);
        Score score = scoreRepository.findByStudent_IdAndCourse_CourseId(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found"));
        return scoreMapper.toResponse(score);
    }

    @Override
    public boolean existsByStudentAndCourse(UUID studentId, UUID semesterId, UUID subjectId) {
        CourseId courseId = new CourseId(semesterId, subjectId);
        return scoreRepository.existsByStudent_IdAndCourse_CourseId(semesterId, courseId);
    }

    @Override
    public void delete(UUID id) {
        Score score = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject Not Found: " + id));
        log.info("Deleting score with id: {}", id);
        scoreRepository.delete(score);
    }

}