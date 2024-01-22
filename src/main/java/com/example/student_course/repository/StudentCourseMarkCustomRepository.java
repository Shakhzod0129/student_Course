package com.example.student_course.repository;

import com.example.student_course.dto.PaginationResultDTO;
import com.example.student_course.dto.StudentCourseMarkFilterDto;
import com.example.student_course.entity.StudentCourseMarkEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentCourseMarkCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PaginationResultDTO<StudentCourseMarkEntity> filter(StudentCourseMarkFilterDto filterDto, Integer page, Integer size) {

        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();


        if (filterDto.getId() != null) {
            builder.append(" and id=:id ");
            params.put("id", filterDto.getId());
        }
        if (filterDto.getStudentId() != null) {
            builder.append(" and studentId=:studentId ");
            params.put("studentId", filterDto.getStudentId());
        }
        if (filterDto.getCourseId() != null) {
            builder.append("and courseId=:courseId ");
            params.put("courseId", filterDto.getCourseId());
        }

        if (filterDto.getMark() != null) {
            builder.append("and mark=:mark ");
            params.put("mark", filterDto.getMark());
        }


        if (filterDto.getFromDate() != null && filterDto.getToDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filterDto.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filterDto.getToDate(), LocalTime.MAX);
            builder.append("and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filterDto.getFromDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filterDto.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filterDto.getFromDate(), LocalTime.MAX);
            builder.append("and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filterDto.getToDate() != null) {
            LocalDateTime toDate = LocalDateTime.of(filterDto.getToDate(), LocalTime.MAX);
            builder.append("and createdDate <= :toDate ");
            params.put("toDate", toDate);
        }

        StringBuilder selectBuilder = new StringBuilder(" FROM StudentCourseMarkEntity s where 1=1 ");
        selectBuilder.append(builder);

        StringBuilder countBuilder = new StringBuilder(" Select count(s) FROM StudentCourseMarkEntity s where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult((page - 1) * size);
        Query countQuery = entityManager.createQuery(countBuilder.toString());

       /* if (filterDto.getId() != null) {
            query.setParameter("id", filterDto.getId());
        }
        if (filterDto.getName() != null) {
            query.setParameter("name", filterDto.getName());
        }
        if(filterDto.getSurname() != null){
            query.setParameter("surname", filterDto.getSurname());
        }*/
        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<StudentCourseMarkEntity> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<StudentCourseMarkEntity>(totalElements, entityList);
    }

}
