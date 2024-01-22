package com.example.student_course.mapper;

import com.example.student_course.dto.CourseDTO;
import com.example.student_course.dto.StudentDTO;

import java.time.LocalDateTime;

public interface StudentCourseMapper {
    Integer getStudentCourseMapperId();

    Double getMark();
    StudentDTO getStudent();
    Integer getCourseId();
    Integer getStudentId();
    String getCName();
    String getSName();
    String getSurname();
    Double getPrice();
    LocalDateTime getCreatedDate();
    Integer getId();
    Integer getDuration();

}
