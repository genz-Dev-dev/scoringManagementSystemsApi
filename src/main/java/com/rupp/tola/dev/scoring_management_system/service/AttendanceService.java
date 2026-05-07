package com.rupp.tola.dev.scoring_management_system.service;

import com.rupp.tola.dev.scoring_management_system.entity.Attendance;
import com.rupp.tola.dev.scoring_management_system.entity.AttendanceRequest;

public interface AttendanceService {

    Attendance create(Attendance request);

    AttendanceRequest createAttendanceStaffRequest( AttendanceRequest attendanceRequest);
}
