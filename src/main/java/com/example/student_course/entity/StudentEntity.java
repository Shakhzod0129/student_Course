package com.example.student_course.entity;

import com.example.student_course.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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

    public StudentEntity() {
    }

    public StudentEntity(Integer studentId) {
        this.id=studentId;
    }
}
