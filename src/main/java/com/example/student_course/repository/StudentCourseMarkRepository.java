package com.example.student_course.repository;

import com.example.student_course.entity.CourseEntity;
import com.example.student_course.entity.StudentCourseMarkEntity;
import com.example.student_course.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer>, PagingAndSortingRepository<StudentCourseMarkEntity, Integer> {

    List<StudentCourseMarkEntity> findByCreatedDate(LocalDate createdDate);


    List<StudentCourseMarkEntity> findByStudentAndCourseOrderByCreatedDateDesc(StudentEntity studentEntity, CourseEntity courseEntity);

    Page<StudentCourseMarkEntity> findAllBy(Pageable pageable);
    Page<StudentCourseMarkEntity> findAllByStudentId(Pageable pageable,Integer studentID);
    Page<StudentCourseMarkEntity> findAllByCourseId(Pageable pageable,Integer courseId);

    Optional<StudentCourseMarkEntity> findFirstByStudentIdOrderByCreatedDateDesc(Integer student_id);

    List<StudentCourseMarkEntity> findTop3ByStudentOrderByMarkDesc(StudentEntity student);
    List<StudentCourseMarkEntity> findFirstMarkByStudentOrderByCreatedDate(StudentEntity student);

    List<StudentCourseMarkEntity> findFirstMarkByStudentIdAndCourseId(Integer student_id, Integer course_id);
    List<StudentCourseMarkEntity> findTop1MarkByStudentIdAndCourseIdOrderByMarkDesc(Integer student_id, Integer course_id);
    List<StudentCourseMarkEntity> findByStudentAndCourse(StudentEntity student_id, CourseEntity course_id);

    Optional<StudentCourseMarkEntity> findFirstByCourseIdOrderByMarkDesc(Integer courseId);
    List<StudentCourseMarkEntity> findByCourseId(Integer courseId);




}
