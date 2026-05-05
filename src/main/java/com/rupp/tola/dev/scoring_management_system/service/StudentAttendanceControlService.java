package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.entity.StudentAttendanceControl;

import java.util.List;

public interface StudentAttendanceControlService {

    List<StudentAttendanceControl> create(List<StudentAttendanceControl> requests);

    List<StudentAttendanceControl> getAll();

}
