package com.example.student_course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentCourseMarkFilterDto {

    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Double mark;
    private StudentDTO student;
    private CourseDTO courseDTO;
    private LocalDate fromDate;
    private LocalDate toDate;

}
