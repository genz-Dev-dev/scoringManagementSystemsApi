package com.kh.rupp_dev.boukryuniversity.specification;

import com.kh.rupp_dev.boukryuniversity.entity.Role;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoleSpecification implements Specification<Role> {

	private static final long serialVersionUID = 1L;
	private RoleFiltter filtter;

	@Override
	public Predicate toPredicate(@NonNull Root<Role> root, @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
		return null;
	}

}
