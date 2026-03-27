package com.rupp.tola.dev.scoring_management_system.service.handler;

import com.rupp.tola.dev.scoring_management_system.dto.request.StudentRequest;
import com.rupp.tola.dev.scoring_management_system.entity.Class;
import com.rupp.tola.dev.scoring_management_system.entity.Student;
import com.rupp.tola.dev.scoring_management_system.entity.StudentAddress;
import com.rupp.tola.dev.scoring_management_system.utils.Util;

public class ClazzServiceHandler {

    public static void updateToClassStudent(Student student , StudentRequest request , Class clazz) {
        student.setClazz(clazz);
        student.setKhFirstName(request.getKhFirstName());
        student.setKhLastName(request.getKhLastName());
        student.setEnFirstName(request.getEnFirstName());
        student.setEnLastName(request.getEnLastName());
        student.setGender(request.getGender());
        student.setDateOfBirth(Util.convertToLocalDate(request.getDateOfBirth()));
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
    }

    public static void updateToStudentAddress(StudentAddress address , StudentRequest request , Student student) {
        address.setStudent(student);
        address.setHouseNumber(request.getAddress().getHouseNumber());
        address.setStreet(request.getAddress().getStreet());
        address.setSangkat(request.getAddress().getSangkat());
        address.setKhan(request.getAddress().getKhan());
        address.setProvince(request.getAddress().getProvince());
        address.setCountry(request.getAddress().getCountry());
    }



}
