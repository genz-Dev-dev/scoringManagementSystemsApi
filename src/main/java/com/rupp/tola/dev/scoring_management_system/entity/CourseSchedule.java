package com.rupp.tola.dev.scoring_management_system.entity;

import com.rupp.tola.dev.scoring_management_system.audit.AuditListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_course_schedule")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditListener.class)
public class CourseSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "course_schedule_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "semester_id", referencedColumnName = "semester_id", nullable = false),
            @JoinColumn(name = "subject_id", referencedColumnName = "subject_id", nullable = false)
    })
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, length = 20)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "room", nullable = false)
    private Integer room;
}
