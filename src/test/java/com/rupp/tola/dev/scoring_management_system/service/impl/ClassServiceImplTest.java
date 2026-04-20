package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.dto.request.DepartmentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.DepartmentResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import com.rupp.tola.dev.scoring_management_system.entity.Department;
import com.rupp.tola.dev.scoring_management_system.repository.ClassRepository;
import com.rupp.tola.dev.scoring_management_system.repository.DepartmentRepository;
import com.rupp.tola.dev.scoring_management_system.service.ClassService;
import com.rupp.tola.dev.scoring_management_system.service.DepartmentService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClassServiceImplTest {

    @Mock
    private ClassRepository classRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private ClassService classService;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void create_shouldReturnSavedClass() {

        DepartmentRequest request = new DepartmentRequest();
        request.setName("Department Name");
        request.setDescription("Department Description");
        request.setImage(null);



        Class clazz = new Class();
        clazz.setDepartment(null);
        clazz.setName("Class Name");

        Class saveClass = classRepository.save(clazz);

        assertEquals("Class Name", saveClass.getName());

    }
}