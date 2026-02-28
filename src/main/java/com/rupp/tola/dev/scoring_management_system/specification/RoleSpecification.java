package com.rupp.tola.dev.scoring_management_system.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import com.rupp.tola.dev.scoring_management_system.entity.Roles;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoleSpecification implements Specification<Roles> {

	private static final long serialVersionUID = 1L;
	private RoleFiltter filtter;

	@Override
	public Predicate toPredicate(@NonNull Root<Roles> root, @NonNull CriteriaQuery<?> query,
			@NonNull CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		return null;
	}

}
