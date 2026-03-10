package com.rupp.tola.dev.scoring_management_system.specification;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import com.rupp.tola.dev.scoring_management_system.entity.Students;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class StudentSpecification implements Specification<Students> {

	@Override
	public @Nullable Predicate toPredicate(Root<Students> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		return null;
	}

}
