package com.rupp.tola.dev.scoring_management_system.service.impl;

import com.rupp.tola.dev.scoring_management_system.entity.Attendance;
import com.rupp.tola.dev.scoring_management_system.entity.User;
import com.rupp.tola.dev.scoring_management_system.repository.AttendanceRepository;
import com.rupp.tola.dev.scoring_management_system.repository.UserRepository;
import com.rupp.tola.dev.scoring_management_system.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    @Override
    public Attendance create(Attendance request) {
        User user = userRepository.findById(request.getUser().getId())
                .orElseThrow(() -> new RuntimeException("user not fount"));
        request.setUser(user);
        request.setWorkDate(LocalDateTime.now());
        return  attendanceRepository.save(request);
    }
}
