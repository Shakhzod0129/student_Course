package com.example.student_course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentFilterDto {

    private Integer id;
    private String name;
    private String surname;
    private String level;
    private Integer age;
    private LocalDate fromDate;
    private LocalDate toDate;

}
