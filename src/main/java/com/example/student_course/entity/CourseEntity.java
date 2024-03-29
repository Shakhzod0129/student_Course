package com.example.student_course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public CourseEntity() {
    }

    public CourseEntity(Integer courseID) {
        this.id=courseID;
    }
}
