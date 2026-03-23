package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.SemesterRequest;
import com.rupp.tola.dev.scoring_management_system.entity.Semester;
import com.rupp.tola.dev.scoring_management_system.repository.SemesterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class SemesterServiceImplTest {

    @Mock
    private SemesterRepository semesterRepository;

    @InjectMocks
    private SemesterServiceImpl semesterService;

    @Test
    void update() {
        SemesterRequest request = new SemesterRequest();
        request.setSemesterNo(10);
        request.setStartAt("2018-01-01");
        request.setEndAt("2018-01-02");
        assertEquals("2018-01-01", request.getStartAt());
        assertEquals("2018-01-02", request.getEndAt());
    }
}