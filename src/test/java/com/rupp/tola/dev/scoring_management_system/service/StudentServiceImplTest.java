package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.dto.request.ImportStudentRequest;
import com.rupp.tola.dev.scoring_management_system.dto.response.StudentResponse;
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import com.rupp.tola.dev.scoring_management_system.mapper.StudentsMapper;
import com.rupp.tola.dev.scoring_management_system.repository.ClassesRepository;
import com.rupp.tola.dev.scoring_management_system.repository.StudentsRepository;
import com.rupp.tola.dev.scoring_management_system.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentsRepository studentsRepository;
    @Mock
    private ClassesRepository classesRepository;
    @Mock
    private StudentsMapper studentsMapper;
    @Mock
    private ExcelService excelService;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void testImportStudent() {
        // Arrange
        UUID classId = UUID.randomUUID();
        ImportStudentRequest request = new ImportStudentRequest();
        request.setClassId(classId.toString());
        MultipartFile file = mock(MultipartFile.class);
        request.setFile(file);

        Student student = new Student();
        List<Student> studentList = Collections.singletonList(student);

        when(file.isEmpty()).thenReturn(false);
        when(excelService.exportStudents(file)).thenReturn(studentList);
        when(classesRepository.findById(classId)).thenReturn(Optional.of(new Class()));
        when(studentsRepository.saveAll(studentList)).thenReturn(studentList);
        when(studentsMapper.toResponse(any(Student.class))).thenReturn(new StudentResponse());

        // Act
        studentService.importStudent(request);

        // Assert
        verify(classesRepository).findById(classId);
        verify(studentsRepository).saveAll(studentList);
        verify(studentsMapper).toResponse(any(Student.class));
    }
}
