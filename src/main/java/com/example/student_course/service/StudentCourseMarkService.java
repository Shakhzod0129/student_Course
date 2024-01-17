package com.example.student_course.service;

import com.example.student_course.dto.CourseDTO;
import com.example.student_course.dto.StudentCourseMarkDTO;
import com.example.student_course.dto.StudentDTO;
import com.example.student_course.entity.CourseEntity;
import com.example.student_course.entity.StudentCourseMarkEntity;
import com.example.student_course.entity.StudentEntity;
import com.example.student_course.exp.AppBadException;
import com.example.student_course.repository.CourseRepository;
import com.example.student_course.repository.StudentCourseMarkRepository;
import com.example.student_course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseMarkService {
    @Autowired
    private StudentCourseMarkRepository studentCourseMarkRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;


    public StudentCourseMarkDTO create(StudentCourseMarkDTO studentCourseMarkDto) {

//        Optional<StudentEntity> studentEntity = studentRepository.findById(studentCourseMarkDto.getStudentId());
        StudentEntity studentEntity = studentService.get(studentCourseMarkDto.getStudentId());
//        Optional<CourseEntity> courseEntity = courseRepository.findById(studentCourseMarkDto.getCourseId());
        CourseEntity courseEntity = courseService.get(studentCourseMarkDto.getCourseId());

        if (studentEntity == null) {
            throw new AppBadException("Student has not found with this id ❌");
        }
        if (courseEntity == null) {
            throw new AppBadException("Course has not found with this id ❌");
        }

        StudentCourseMarkEntity studentCourseMarkEntity = new StudentCourseMarkEntity();
        studentCourseMarkEntity.setStudent(studentEntity);
        studentCourseMarkEntity.setCourse(courseEntity);
        studentCourseMarkEntity.setMark(studentCourseMarkDto.getMark());
        studentCourseMarkEntity.setCreatedDate(LocalDateTime.now());

        studentCourseMarkRepository.save(studentCourseMarkEntity);

        studentCourseMarkDto.setCreatedDate(studentCourseMarkEntity.getCreatedDate());
        studentCourseMarkDto.setId(studentCourseMarkEntity.getId());
        return studentCourseMarkDto;
    }


    public Boolean update(Integer id, StudentCourseMarkDTO dto) {

        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Not found ❌❌❌");
        }

        Optional<StudentEntity> student = studentRepository.findById(dto.getStudentId());
        Optional<CourseEntity> course = courseRepository.findById(dto.getCourseId());

        StudentCourseMarkEntity studentCourseMarkEntity = optional.get();

        studentCourseMarkEntity.setStudent(student.get());
        studentCourseMarkEntity.setCourse(course.get());
        studentCourseMarkEntity.setMark(dto.getMark());

        studentCourseMarkRepository.save(studentCourseMarkEntity);
        return true;


    }

    public List<StudentCourseMarkDTO> getAll() {
        Iterable<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll();

        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        for (StudentCourseMarkEntity entity : all) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

    public StudentCourseMarkDTO getById(Integer id) {
        Optional<StudentCourseMarkEntity> optionalStudentCourseMark = studentCourseMarkRepository.findById(id);

        if (optionalStudentCourseMark.isEmpty()) {
            throw new AppBadException("Not Found ❌❌❌");
        }

        StudentCourseMarkEntity studentCourseMarkEntity = optionalStudentCourseMark.get();
        StudentCourseMarkDTO dto = toDto(studentCourseMarkEntity);

        return dto;

    }

    public StudentCourseMarkDTO toDto(StudentCourseMarkEntity entity) {
        StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
        dto.setStudentId(entity.getStudent().getId());
        dto.setCourseId(entity.getCourse().getId());
        dto.setMark(entity.getMark());
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public StudentCourseMarkDTO getWithDetail(Integer id) {

        StudentCourseMarkEntity entity = get(id);

        StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
        dto.setMark(entity.getMark());
        dto.setCreatedDate(entity.getCreatedDate());

        StudentEntity studentEntity = entity.getStudent();
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(studentEntity.getName());
        studentDTO.setSurname(studentEntity.getSurname());
        studentDTO.setId(studentEntity.getId());

        CourseEntity courseEntity = entity.getCourse();
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName(courseEntity.getName());
        courseDTO.setPrice(courseEntity.getPrice());
        courseDTO.setId(courseEntity.getId());

        dto.setStudent(studentDTO);
        dto.setCourseDTO(courseDTO);

        return dto;

    }


    public List<StudentCourseMarkDTO> getMarkByCourse(Integer studentId, Integer courseId) {

        StudentEntity student = new StudentEntity();
        student.setId(studentId);

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(courseId);

        List<StudentCourseMarkEntity> byStudentAndCourseOrderByCreatedDateDesc =
                studentCourseMarkRepository.findByStudentAndCourseOrderByCreatedDateDesc(student, courseEntity);

        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        for (StudentCourseMarkEntity studentCourseMarkEntity : byStudentAndCourseOrderByCreatedDateDesc) {
            dtoList.add(toDto(studentCourseMarkEntity));
        }

        return dtoList;
    }

    public PageImpl<StudentCourseMarkDTO> pagination(int page, int size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<StudentCourseMarkEntity> allBy = studentCourseMarkRepository.findAllBy(pageable);

        List<StudentCourseMarkEntity> content = allBy.getContent();
        long totalElements = allBy.getTotalElements();

        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();

        for (StudentCourseMarkEntity studentCourseMarkEntity : content) {
            dtoList.add(toDto(studentCourseMarkEntity));
        }

        return new PageImpl<>(dtoList, pageable, totalElements);
    }
//----------------------------------------------------------------------------------------------------
    public PageImpl<StudentCourseMarkDTO> paginationByStudentId(int page, int size, Integer studentId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<StudentCourseMarkEntity> allByStudentId = studentCourseMarkRepository.findAllByStudentId(pageable, studentId);

        List<StudentCourseMarkEntity> content = allByStudentId.getContent();
        long totalElements = allByStudentId.getTotalElements();
        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        for (StudentCourseMarkEntity studentCourseMarkEntity : content) {
            dtoList.add(toDto(studentCourseMarkEntity));
        }

        return new PageImpl<>(dtoList, pageable, totalElements);
    }
//----------------------------------------------------------------------------------------------------
    public PageImpl<StudentCourseMarkDTO> paginationByCourseId(int page, int size, Integer courseId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<StudentCourseMarkEntity> allByCourseId = studentCourseMarkRepository.findAllByCourseId(pageable, courseId);

        List<StudentCourseMarkEntity> content = allByCourseId.getContent();
        long totalElements = allByCourseId.getTotalElements();

        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();

        for (StudentCourseMarkEntity studentCourseMarkEntity : content) {
            dtoList.add(toDto(studentCourseMarkEntity));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public List<StudentCourseMarkDTO> getLastMarkAndCourse(Integer studentID) {

//        StudentEntity studentEntity=new StudentEntity();
//        studentEntity.setId(studentID);

        Optional<StudentCourseMarkEntity> optional =
                studentCourseMarkRepository.findFirstByStudentIdOrderByCreatedDateDesc(studentID);

        if (optional.isEmpty()) {
            throw new AppBadException("Student not found❌❌❌");
        }
        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();

        StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
        dto.setMark(optional.get().getMark());

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName(optional.get().getCourse().getName());
        dto.setCourseDTO(courseDTO);
        dtoList.add(dto);


        return dtoList;

    }

    public StudentCourseMarkEntity get(Integer id) {
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        return optional.get();
    }

    public List<StudentCourseMarkDTO> getTop3Mark(Integer studentID) {

        StudentEntity studentEntity=new StudentEntity(studentID);
        List<StudentCourseMarkEntity> top3ByStudentOrderByMarkDesc =
                studentCourseMarkRepository.findTop3ByStudentOrderByMarkDesc(studentEntity);
        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        for (StudentCourseMarkEntity entity : top3ByStudentOrderByMarkDesc) {
            dtoList.add(toDto(entity));
        }


        return dtoList;
    }

    public List<StudentCourseMarkDTO> getFirstMarkByStudent(Integer studentID) {

        StudentEntity studentEntity=new StudentEntity(studentID);

        List<StudentCourseMarkEntity> firstByStudentOrderByMark = studentCourseMarkRepository.findFirstMarkByStudentOrderByCreatedDate(studentEntity);

        List<StudentCourseMarkDTO> dtoList=new LinkedList<>();

        for (StudentCourseMarkEntity studentCourseMarkEntity : firstByStudentOrderByMark) {
            StudentCourseMarkDTO dto=new StudentCourseMarkDTO();
            dto.setMark(studentCourseMarkEntity.getMark());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<StudentCourseMarkDTO> getFirstMarkFromCourse(Integer studentID, Integer courseID) {

        List<StudentCourseMarkEntity> firstMarkByStudentIdAndCourseId =
                studentCourseMarkRepository.findFirstMarkByStudentIdAndCourseId(studentID, courseID);
        List<StudentCourseMarkDTO> dtoList=new LinkedList<>();

        for (StudentCourseMarkEntity studentCourseMarkEntity : firstMarkByStudentIdAndCourseId) {
            dtoList.add(toDto(studentCourseMarkEntity));
        }
        return dtoList;
    }

    public List<StudentCourseMarkDTO> getHighestMarkFromCourse(Integer studentID, Integer courseID) {

        List<StudentCourseMarkEntity> top1MarkByStudentIdAndCourseId =
                studentCourseMarkRepository.findTop1MarkByStudentIdAndCourseIdOrderByMarkDesc(studentID, courseID);

        List<StudentCourseMarkDTO> dtoList=new LinkedList<>();
        for (StudentCourseMarkEntity studentCourseMarkEntity : top1MarkByStudentIdAndCourseId) {
            dtoList.add(toDto(studentCourseMarkEntity));
        }
        return dtoList;
    }

    public Double getAverageMark(Integer studentID) {

        Optional<StudentEntity> optional = studentRepository.findById(studentID);

        if(optional.isEmpty()){
            throw new AppBadException("Student not found❌❌❌");
        }
        double marks=0;
        int count=0;

        Iterable<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll();

        for (StudentCourseMarkEntity studentCourseMarkEntity : all) {
            if(studentCourseMarkEntity.getStudent().getId().equals(optional.get().getId())){
                marks+=studentCourseMarkEntity.getMark();
                count++;
            }

        }

        return marks/count;

    }

    public Double getAverageMarkFromCourse(Integer studentID, Integer courseID) {

        StudentEntity student=new StudentEntity(studentID);
        CourseEntity course=new CourseEntity(courseID);
        List<StudentCourseMarkEntity> byStudentIdAndCourseId =
                studentCourseMarkRepository.findByStudentAndCourse(student, course);


        double marks=0;
        int count=0;
        for (StudentCourseMarkEntity studentCourseMarkEntity : byStudentIdAndCourseId) {
                marks+=studentCourseMarkEntity.getMark();
                count++;

        }


        return marks/count;
    }

    public List<Double> getHighMarksThanGivenMark(Integer studentID, Integer mark) {

        StudentEntity studentEntity = studentService.get(studentID);

        Iterable<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll();

        List<Double> marks=new LinkedList<>();

        for (StudentCourseMarkEntity studentCourseMarkEntity : all) {
            if(studentCourseMarkEntity.getStudent().getId().equals(studentEntity.getId())){
                if(studentCourseMarkEntity.getMark()>mark){
                    marks.add(studentCourseMarkEntity.getMark());
                }
            }
        }

        return marks;

    }

    public Double getHighMarksGivenCourse(Integer courseID) {
        Optional<StudentCourseMarkEntity> firstByCourseIdOrderByMark =
                studentCourseMarkRepository.findFirstByCourseIdOrderByMarkDesc(courseID);

        return firstByCourseIdOrderByMark.get().getMark();

    }

    public Double getAverageMarkGivenCourse(Integer courseID) {
        List<StudentCourseMarkEntity> byCourseId = studentCourseMarkRepository.findByCourseId(courseID);
        double marks=0;
        int count=0;

        for (StudentCourseMarkEntity studentCourseMarkEntity : byCourseId) {
            marks+=studentCourseMarkEntity.getMark();
            count++;
        }

        return marks/count;

    }

    public Integer getCountMarkByGivenCourse(Integer courseID) {
        List<StudentCourseMarkEntity> byCourseId = studentCourseMarkRepository.findByCourseId(courseID);
        int count=0;
        for (StudentCourseMarkEntity studentCourseMarkEntity : byCourseId) {
            count++;
        }
        return count;
    }
}



//