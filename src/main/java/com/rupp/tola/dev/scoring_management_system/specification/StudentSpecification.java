package com.rupp.tola.dev.scoring_management_system.specification;

import com.rupp.tola.dev.scoring_management_system.dto.request.StudentsFilterRequest;
import com.rupp.tola.dev.scoring_management_system.entity.*;
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {
	public static Specification<Student> filter(StudentsFilterRequest request) {

		return (root, query, cb) -> {

			query.distinct(true);

			// Student → Class
			Join<Student, Class> classJoin = root.join("clazz", JoinType.LEFT);

			// Class → Department
			Join<Class, Department> departmentJoin =
					classJoin.join("department", JoinType.LEFT);

			List<Predicate> predicates = new ArrayList<>();

			// ✅ Class filter
			if (request.getClassId() != null) {
				predicates.add(cb.equal(classJoin.get("id"), request.getClassId()));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}