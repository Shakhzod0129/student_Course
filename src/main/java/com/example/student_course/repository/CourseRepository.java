package com.example.student_course.repository;
import com.example.student_course.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface CourseRepository extends CrudRepository<CourseEntity, Integer>, PagingAndSortingRepository<CourseEntity, Integer> {
    List<CourseEntity> findByName(String name);

    List<CourseEntity> findByPrice(Double price);

    List<CourseEntity> findByDuration(Integer duration);

    List<CourseEntity> findByPriceBetween(Double fromPrice, Double toPrice);

    List<CourseEntity> findByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    Page<CourseEntity>  findAll(Pageable page);
    Page<CourseEntity>  findAllByOrderByCreatedDateDesc(Pageable page);
    Page<CourseEntity>  findAllByPrice(Pageable page, Double price);
    Page<CourseEntity>  findAllByPriceBetween(Pageable page, Double fromPrice, Double toPrice);


}
