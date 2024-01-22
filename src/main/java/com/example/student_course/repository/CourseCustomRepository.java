package com.example.student_course.repository;

import com.example.student_course.dto.CourseFilterDto;
import com.example.student_course.dto.PaginationResultDTO;
import com.example.student_course.entity.CourseEntity;
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
public class CourseCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PaginationResultDTO<CourseEntity> filter(CourseFilterDto dto, int page, int size){
        StringBuilder builder=new StringBuilder();
        Map<String , Object> params=new HashMap<>();
        if(dto.getId()!=null){
            builder.append(" and id=:id ");
            params.put("id",dto.getId());
        }
        if(dto.getName()!=null){
            builder.append(" and name=:name ");
            params.put("name",dto.getName());
        }
        if(dto.getPrice()!=null){
            builder.append(" and price=:price ");
            params.put("price",dto.getPrice());
        }
        if(dto.getDuration()!=null){
            builder.append(" and duration=:duration ");
            params.put("duration",dto.getDuration());
        }
        if(dto.getFromDate()!=null&&dto.getToDate()!=null){
            LocalDateTime fromDate=LocalDateTime.of(dto.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate=LocalDateTime.of(dto.getToDate(), LocalTime.MAX);

            builder.append(" and createdDate between :fromDate and :toDate ");
            params.put("fromDate",fromDate);
            params.put("toDate",toDate);
        }
        else if(dto.getFromDate()!=null){
            LocalDateTime fromDate=LocalDateTime.of(dto.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate=LocalDateTime.of(dto.getFromDate(), LocalTime.MAX);

            builder.append(" and createdDate between :fromDate and :toDate ");
            params.put("fromDate",fromDate);
            params.put("toDate",toDate);
        }
        else if(dto.getToDate()!=null){
            LocalDateTime toDate=LocalDateTime.of(dto.getToDate(), LocalTime.MAX);

            builder.append(" and createdDate <= :toDate ");
            params.put("toDate",toDate);
        }

        StringBuilder selectBuilder=new StringBuilder("from CourseEntity c where 1=1 ");
        selectBuilder.append(builder);

        StringBuilder countBuilder =new StringBuilder("select count(*) from CourseEntity c where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult((page-1)*size);

        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(),param.getValue());
            countQuery.setParameter(param.getKey(),param.getValue());
        }

        List<CourseEntity> entities = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<CourseEntity>(totalElements, entities);



    }
}
