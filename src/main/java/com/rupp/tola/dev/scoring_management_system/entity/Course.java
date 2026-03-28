package com.rupp.tola.dev.scoring_management_system.entity;

import com.rupp.tola.dev.scoring_management_system.entity.composite.CourseId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_course")
@Getter
@Setter
@NoArgsConstructor
public class Course {

    @EmbeddedId
    private CourseId courseId;

    @Column(name = "course_name" , nullable = false , length = 50)
    private String name;

    @Column(name = "course_description" , nullable = false , length = 100)
    private String description;

    @Column(name = "course_schedule" , nullable = false)
    private LocalDate schedule;

    @Column(name = "course_room" , nullable = false)
    private int room;

    @Column(name = "start_at" , nullable = false)
    private LocalDate startAt;

    @Column(name = "end_at" , nullable = false)
    private LocalDate endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id" , referencedColumnName = "user_id")
    private User instructor;

}
