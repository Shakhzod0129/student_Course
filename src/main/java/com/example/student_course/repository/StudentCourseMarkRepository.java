package com.example.student_course.repository;

import com.example.student_course.entity.CourseEntity;
import com.example.student_course.entity.StudentCourseMarkEntity;
import com.example.student_course.entity.StudentEntity;
import com.example.student_course.mapper.StudentCourseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

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

    @Query("from StudentCourseMarkEntity as scm where scm.student.id=?1 order by scm.createdDate desc limit 1")
    Optional<StudentCourseMarkEntity> findByStudentId(Integer studentId);


    @Query("from StudentCourseMarkEntity as scm where scm.student.name=?1 order by scm.createdDate desc limit 1")
    Optional<StudentCourseMarkEntity> findByStudentName(String name);
    @Query("select s from StudentCourseMarkEntity scm " +
            "inner join scm.student s where s.name=:sName")
    List<StudentEntity> joinExample(@Param("sName") String name);

    @Query("SELECT s.id, s.name, s.surname," +
            " c.id, c.name" +
            " from StudentCourseMarkEntity scm " +
            " inner join scm.student s " +
            " inner join scm.course c " +
            " where s.name =:sName and c.name =:cName ")
    List<Object[]> joinExample4(@Param("sName") String sName, @Param("cName") String cName);


    @Query(value = "select scm.id,scm.mark,c.name,c.price from course c "+
            "inner join student_course_mark scm on c.id=scm.id where student_id=?1", nativeQuery = true)
    List<StudentCourseMapper> getAllMarksByStudentId(Integer studentId);

    @Query(value = "select scm.id,scm.mark,scm.created_date as createdDate,c.id as courseId,c.name from  course c  " +
            "inner join student_course_mark scm on c.id=scm.course_id where student_id=?1", nativeQuery = true)
    List<StudentCourseMapper> getByStudentId(Integer id);

    @Query(value = "select scm.id,scm.mark, scm.created_date, s.id, s.name, s.surname from  student s  " +
            "inner join student_course_mark scm on s.id=scm.student_id where course_id=?1", nativeQuery = true)
    List<StudentCourseMapper> getByCourseId(Integer courseId);

    @Query(value = "select scm.id,scm.mark, scm.created_date, s.id, s.name as sname, s.surname,c.name as cname,c.price,c.id from  student_course_mark scm  " +
            "inner join student s on s.id=scm.student_id " +
            "inner join course c on scm.course_id=c.id", nativeQuery = true)
    List<StudentCourseMapper> getAllMark();


}
