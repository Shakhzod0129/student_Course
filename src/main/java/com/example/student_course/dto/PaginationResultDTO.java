package com.example.student_course.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginationResultDTO<T> {
    private Long totalSize;
    private List<T> list;

    public PaginationResultDTO() {
    }

    public PaginationResultDTO(Long totalSize, List<T> list) {
        this.totalSize = totalSize;
        this.list = list;
    }

}
