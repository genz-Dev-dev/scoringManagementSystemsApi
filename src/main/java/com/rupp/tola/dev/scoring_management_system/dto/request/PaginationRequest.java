package com.rupp.tola.dev.scoring_management_system.dto.request;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PaginationRequest {
    private int page = 0;
    private int size = 10;
    private String sortBy = "id";
    private boolean ascending = true;

    /**
     * Helper method use in pagination passed as parameter spring boot know It as a request params
     * @return
     */
    public Pageable toPageable() {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return PageRequest.of(page , size , sort);
    }
}
