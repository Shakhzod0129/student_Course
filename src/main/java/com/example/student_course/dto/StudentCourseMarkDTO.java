package com.example.student_course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentCourseMarkDTO {

    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Double mark;
    private LocalDateTime createdDate;
    private StudentDTO student;
    private CourseDTO courseDTO;
}
