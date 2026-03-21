package com.rupp.tola.dev.scoring_management_system.specification;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import com.rupp.tola.dev.scoring_management_system.entity.Student;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class StudentSpecification implements Specification<Student> {

	@Override
	public @Nullable Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query,
                                           CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		return null;
	}

}
