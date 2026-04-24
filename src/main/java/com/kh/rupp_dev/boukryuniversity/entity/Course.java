package com.kh.rupp_dev.boukryuniversity.entity;

import com.kh.rupp_dev.boukryuniversity.entity.composite.CourseId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_course")
@Getter
@Setter
@NoArgsConstructor
public class Course{

    @EmbeddedId
    private CourseId courseId;

    @Column(name = "course_name" , nullable = false , length = 50)
    private String name;

    @Column(name = "course_description" , nullable = false , length = 100)
    private String description;

    @Column(name = "start_at" , nullable = false)
    private LocalDate startAt;

    @Column(name = "end_at" , nullable = false)
    private LocalDate endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id" , referencedColumnName = "user_id")
    private User instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseSchedule> schedules = new ArrayList<>();

}
