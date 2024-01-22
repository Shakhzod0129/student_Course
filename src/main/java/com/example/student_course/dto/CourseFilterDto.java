package com.example.student_course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseFilterDto {

    private Integer id;
    private String name;
    private Double price;
    private Integer duration;
    private LocalDate fromDate;
    private LocalDate toDate;
}
