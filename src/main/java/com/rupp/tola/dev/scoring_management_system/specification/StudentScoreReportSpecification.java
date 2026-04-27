package com.rupp.tola.dev.scoring_management_system.specification;

import com.rupp.tola.dev.scoring_management_system.entity.Score;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentScoreReportSpecification{


    public static Specification<Score> report() {
        return (root, query, cb) -> {

            // prevent duplicate rows
            query.distinct(true);

            // fetch joins (avoid N+1 problem)
            root.fetch("student");
            root.fetch("subject");
            root.fetch("semester");

            return cb.conjunction();
        };
    }
}
