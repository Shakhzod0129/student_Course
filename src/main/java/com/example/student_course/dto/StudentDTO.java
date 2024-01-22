package com.example.student_course.dto;

import com.example.student_course.enums.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO {

    private Integer id;
    private String name;
    private String surname;
    private Integer level;
    private Integer age;
    private Gender gender;
    private LocalDateTime createdDate;
//    private StudentDTO studentDTO;


}
