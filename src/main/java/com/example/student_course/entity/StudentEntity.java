package com.example.student_course.entity;

import com.example.student_course.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "student")

public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "level")
    private Integer level;
    @Column(name = "age")
    private Integer age;
    @Enumerated(EnumType. STRING)
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @OneToMany(mappedBy = "student")
    private List<StudentCourseMarkEntity> studentCourseMarkEntityList;

    public StudentEntity() {
    }

    public StudentEntity(Integer id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public StudentEntity(Integer studentId) {
        this.id=studentId;
    }
}
