package com.example.student_course.repository;

import com.example.student_course.dto.PaginationResultDTO;
import com.example.student_course.dto.StudentDTO;
import com.example.student_course.dto.StudentFilterDto;
import com.example.student_course.entity.StudentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentCustomRepository {
    @Autowired
    private EntityManager entityManager;
    public void filter(StudentFilterDto filterDto, Integer page, Integer sze ){

        StringBuilder builder=new StringBuilder("from StudentEntity s where 1=1 ");
        Map<String, Object> params=new HashMap<>();


        if(filterDto.getId() !=null){
            builder.append(" and id=:id");
            params.put("id",filterDto.getId());
        }
        if(filterDto.getName()!=null){
            builder.append(" and name=:name");
            params.put("name", filterDto.getName());
        }
        if(filterDto.getSurname()!=null){
            builder.append(" and lower(surname) like:surname");
            params.put("surname", "%"+filterDto.getSurname().toLowerCase()+"%");
        }

        if(filterDto.getFromDate()!=null){
            LocalDateTime fromDate = LocalDateTime.of(filterDto.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filterDto.getFromDate(), LocalTime.MAX);
            builder.append(" and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        }
        if(filterDto.getFromDate()!=null){
            LocalDateTime toDate = LocalDateTime.of(filterDto.getFromDate(), LocalTime.MAX);
            builder.append(" and createdDate >:toDate ");
            params.put("toDate", toDate);
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
            builder.append("and createdDate < :toDate ");
            params.put("toDate", toDate);
        }


        Query query = entityManager.createQuery(builder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        List<StudentEntity> entities=query.getResultList();

    }

    public PaginationResultDTO<StudentEntity> filter2(StudentFilterDto filter, int page, int size) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filter.getId() != null) {
            builder.append("and id =:id ");
            params.put("id", filter.getId());
        }
        if (filter.getName() != null) {
            builder.append("and name =:name ");
            params.put("name", filter.getName());
        }
        if (filter.getSurname() != null) {
            builder.append("and lower(surname) like :surname ");
            params.put("surname", "%" + filter.getSurname().toLowerCase() + "%");
        }
        if (filter.getFromDate() != null && filter.getToDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getToDate(), LocalTime.MAX);
            builder.append("and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getFromDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MAX);
            builder.append("and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getToDate() != null) {
            LocalDateTime toDate = LocalDateTime.of(filter.getToDate(), LocalTime.MAX);
            builder.append("and createdDate <= :toDate ");
            params.put("toDate", toDate);
        }

        StringBuilder selectBuilder = new StringBuilder("FROM StudentEntity s where 1=1 ");
        selectBuilder.append(builder);

        StringBuilder countBuilder = new StringBuilder("Select count(s) FROM StudentEntity s where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult((page-1)*size);
        Query countQuery = entityManager.createQuery(countBuilder.toString());

       /* if (filter.getId() != null) {
            query.setParameter("id", filter.getId());
        }
        if (filter.getName() != null) {
            query.setParameter("name", filter.getName());
        }
        if(filter.getSurname() != null){
            query.setParameter("surname", filter.getSurname());
        }*/
        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<StudentEntity> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<StudentEntity>(totalElements,entityList);
    }


}
