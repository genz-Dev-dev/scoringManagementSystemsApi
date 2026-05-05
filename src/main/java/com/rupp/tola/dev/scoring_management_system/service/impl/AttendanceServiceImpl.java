    package com.rupp.tola.dev.scoring_management_system.service.impl;

    import com.rupp.tola.dev.scoring_management_system.entity.Attendance;
    import com.rupp.tola.dev.scoring_management_system.entity.User;
    import com.rupp.tola.dev.scoring_management_system.repository.AttendanceRepository;
    import com.rupp.tola.dev.scoring_management_system.repository.UserRepository;
    import com.rupp.tola.dev.scoring_management_system.service.AttendanceService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.scheduling.annotation.Scheduled;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class AttendanceServiceImpl implements AttendanceService {

        private final AttendanceRepository attendanceRepository;
        private final UserRepository userRepository;

        @Override
        public Attendance create(Attendance request) {

            LocalDate today = LocalDate.now();
            LocalDateTime now = LocalDateTime.now();

            User user = userRepository.findById(request.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Attendance attendance = attendanceRepository
                    .findByUserIdAndWorkDate(user.getId(), today)
                    .orElseGet(() -> {
                        Attendance newAttendance = new Attendance();
                        newAttendance.setUser(user);
                        newAttendance.setWorkDate(today);
                        newAttendance.setStatus("ABS");
                        return newAttendance;
                    });

            if (now.isBefore(request.getStartDate()) || now.isEqual(request.getStartDate())) {
                attendance.setStatus("ACTIVE");
            } else {
                attendance.setStatus("LATE");
            }

            attendance.setStartDate(request.getStartDate());
            attendance.setEndDate(request.getEndDate());

            return attendanceRepository.save(attendance);
        }

        @Scheduled(cron = "0 30 17 * * *", zone = "Asia/Phnom_Penh")
        public void markAbsentAtFiveThirtyPM() {

            LocalDate today = LocalDate.now();

            List<User> users = userRepository.findAll();

            for (User user : users) {

                Attendance attendance = attendanceRepository
                        .findByUserIdAndWorkDate(user.getId(), today)
                        .orElseGet(() -> {
                            Attendance newAttendance = new Attendance();
                            newAttendance.setUser(user);
                            newAttendance.setWorkDate(today);
                            newAttendance.setStatus("ABS");
                            return newAttendance;
                        });

                if (attendance.getStatus() == null) {
                    attendance.setStatus("ABS");
                }

                attendanceRepository.save(attendance);
            }
        }

        public void initializeAttendance(List<User> users) {

            LocalDate today = LocalDate.now();

            for (User user : users) {

                boolean exists = attendanceRepository
                        .existsByUserIdAndWorkDate(user.getId(), today);

                if (!exists) {
                    Attendance attendance = new Attendance();
                    attendance.setUser(user);
                    attendance.setWorkDate(today);
                    attendance.setStatus("ABS");

                    attendanceRepository.save(attendance);
                }
            }
        }
    }