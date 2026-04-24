package com.kh.rupp_dev.boukryuniversity.service.impl;

import com.kh.rupp_dev.boukryuniversity.dto.request.DepartmentRequest;
import com.kh.rupp_dev.boukryuniversity.entity.Class;
import com.kh.rupp_dev.boukryuniversity.repository.ClassRepository;
import com.kh.rupp_dev.boukryuniversity.repository.DepartmentRepository;
import com.kh.rupp_dev.boukryuniversity.service.ClassService;
import com.kh.rupp_dev.boukryuniversity.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
        request.setThumbnail(null);



        Class clazz = new Class();
        clazz.setDepartment(null);
        clazz.setName("Class Name");

        Class saveClass = classRepository.save(clazz);

        assertEquals("Class Name", saveClass.getName());

    }
}