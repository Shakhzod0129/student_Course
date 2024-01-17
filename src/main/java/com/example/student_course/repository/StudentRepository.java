package com.example.student_course.repository;

import com.example.student_course.entity.StudentEntity;
import com.example.student_course.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer>,PagingAndSortingRepository<StudentEntity, Integer> {

    List<StudentEntity> findByName(String name);

    List<StudentEntity> findBySurname(String surname);

    List<StudentEntity> findByLevel(Integer level);

    List<StudentEntity> findByAge(Integer age);

    List<StudentEntity> findByGender(Gender gender);

    Optional<StudentEntity> findFirstByName(String name);

    Long countByNameIgnoreCase(String name);

    Page<StudentEntity> findAllBy(Pageable pageable);

    Page<StudentEntity> findAllByLevel(Pageable pageable,Integer level);
    Page<StudentEntity> findAllByGender(Pageable pageable,Gender gender);

    List<StudentEntity> findAllByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    List<StudentEntity>  findAllByName(String name, Sort sort);

    //---------=-----------=-----------=-------------=---------=--------------------------

//    @Query("from StudentEntity  where name=:nameInput")
//    List<StudentEntity> findbyName1(@Param("nmaeInput") String name);
//    @Query("from StudentEntity  where name=:nameInput order by age desc " )
//    List<StudentEntity> findbyName2(@Param("nameInput") String name);
//
//    @Query("from StudentEntity  where name=:nameInput order by age desc limit 10" )
//    List<StudentEntity> findbyName3(@Param("nameInput") String name);
//
//    @Query("from StudentEntity  where name=:nameInput and surname=:surnameInput order by age desc limit 10" )
//    List<StudentEntity> findbyName4(@Param("nameInput") String name,@Param("surnameInput") String surname);
//
//    @Query("from StudentEntity  where name=?1 and surname=?2 order by age desc limit ?3" )
//    List<StudentEntity> findbyName5(@Param("nameInput") String name,@Param("surnameInput") String surname, int limit);
//    @Query("select count(s.id) from StudentEntity  s where s.name=:nameInput order by s.age  desc ")
//    Long countByName(@Param("nameInput") String name);
//    @Query("select s.id, s.name, s.surname from StudentEntity s")
//    List<StudentEntity> getShortInfo();
//
//    @Query("from StudentEntity where name=?1 or surname=?2")
//    List<StudentEntity> findByNameWithSort(String name, String surname, Sort sort);


}
