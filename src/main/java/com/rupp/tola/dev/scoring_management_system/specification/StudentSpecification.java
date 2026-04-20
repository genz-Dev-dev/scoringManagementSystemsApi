package com.rupp.tola.dev.scoring_management_system.specification;

import com.rupp.tola.dev.scoring_management_system.dto.request.StudentsFilterRequest;
import com.rupp.tola.dev.scoring_management_system.entity.*;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.lang.Class;
import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {
	public static Specification<Student> filter(StudentsFilterRequest request) {
		return (root, query, cb) -> {

			query.distinct(true);

			Join<Student, Class> classJoin = root.join("clazz", JoinType.LEFT);

			Join<Class, Department> departmentJoin =
					classJoin.join("department", JoinType.LEFT);

			Join<Class, CourseSchedule> scheduleJoin =
					classJoin.join("courseSchedules", JoinType.LEFT);

			Join<CourseSchedule, Subject> subjectJoin =
					scheduleJoin.join("subject", JoinType.LEFT);

			Join<CourseSchedule, Semester> semesterJoin =
					scheduleJoin.join("semester", JoinType.LEFT);

			List<Predicate> predicates = new ArrayList<>();

			if (request.getClassId() != null) {
				predicates.add(cb.equal(classJoin.get("id"), request.getClassId()));
			}

			if (request.getDepartmentId() != null) {
				predicates.add(cb.equal(departmentJoin.get("id"), request.getDepartmentId()));
			}

			if (request.getSubjectId() != null) {
				predicates.add(cb.equal(subjectJoin.get("id"), request.getSubjectId()));
			}

			if (request.getSemsterId() != null) {
				predicates.add(cb.equal(semesterJoin.get("id"), request.getSemsterId()));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
