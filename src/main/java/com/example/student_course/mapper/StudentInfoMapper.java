package com.example.student_course.mapper;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentInfoMapper {
    private Integer id;
    private String name;
    private Integer level;

    public StudentInfoMapper(Integer id, String name, Integer level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }
}
