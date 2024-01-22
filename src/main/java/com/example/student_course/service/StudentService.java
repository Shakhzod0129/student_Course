package com.example.student_course.service;

import com.example.student_course.dto.PaginationResultDTO;
import com.example.student_course.dto.StudentDTO;
import com.example.student_course.dto.StudentFilterDto;
import com.example.student_course.entity.StudentEntity;
import com.example.student_course.enums.Gender;
import com.example.student_course.exp.AppBadException;
import com.example.student_course.mapper.StudentMapper;
import com.example.student_course.repository.StudentCustomRepository;
import com.example.student_course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentCustomRepository studentCustomRepository;

    public StudentDTO create(StudentDTO dto) {
        StudentEntity entity = new StudentEntity();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setAge(dto.getAge());
        entity.setLevel(dto.getLevel());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setGender(dto.getGender());

        studentRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<StudentDTO> getAll() {
        Iterable<StudentEntity> all = studentRepository.findAll();

        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentEntity entity : all) {
            dtoList.add(toDto(entity));
        }

        return dtoList;
    }

    public StudentDTO getById(Integer id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);

        if (optional.isEmpty()) {
            throw new AppBadException("Student has not found with this id❌");
        }

        StudentEntity entity = optional.get();

        return toDto(entity);

    }


    public StudentDTO toDto(StudentEntity entity) {
        StudentDTO dto = new StudentDTO();

        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setLevel(entity.getLevel());
        dto.setAge(entity.getAge());
        dto.setGender(entity.getGender());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }


    public Boolean update(Integer id, StudentDTO dto) {
        Optional<StudentEntity> optional = studentRepository.findById(id);

        if (optional.isEmpty()) {
            throw new AppBadException("Student has not found with this id❌");
        }

        StudentEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setLevel(dto.getLevel());
        entity.setAge(dto.getAge());

        studentRepository.save(entity);

        return true;

    }

    public Boolean delete(Integer id) {

        Optional<StudentEntity> optional = studentRepository.findById(id);

        if (optional.isEmpty()) {
            throw new AppBadException("Student has not found with this id❌");
        }

        studentRepository.delete(optional.get());
        return true;

    }

    public List<StudentDTO> findByName(String name) {

        List<StudentEntity> studentEntity = studentRepository.findByName(name);

        List<StudentDTO> dtoList = new LinkedList<>();

        for (StudentEntity entity : studentEntity) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

    public List<StudentDTO> findBySurname(String surname) {

        List<StudentEntity> entities = studentRepository.findBySurname(surname);
        List<StudentDTO> dtoList = new LinkedList<>();

        for (StudentEntity entity : entities) {
            dtoList.add(toDto(entity));
        }

        return dtoList;
    }

    public List<StudentDTO> findByLevel(Integer level) {
        List<StudentEntity> entities = studentRepository.findByLevel(level);
        List<StudentDTO> dtoList = new LinkedList<>();

        for (StudentEntity entity : entities) {
            dtoList.add(toDto(entity));
        }

        return dtoList;

    }

    //
    public List<StudentDTO> findByAge(Integer age) {
        List<StudentEntity> entities = studentRepository.findByAge(age);
        List<StudentDTO> dtoList = new LinkedList<>();

        for (StudentEntity entity : entities) {
            dtoList.add(toDto(entity));
        }

        return dtoList;

    }

    //
    public List<StudentDTO> findByGender(Gender gender) {
        List<StudentEntity> entities = studentRepository.findByGender(gender);
        List<StudentDTO> dtoList = new LinkedList<>();

        for (StudentEntity entity : entities) {
            dtoList.add(toDto(entity));
        }

        return dtoList;

    }
    public StudentEntity get(Integer id){
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        return optional.get();
    }


    public StudentDTO firtByName(String name) {
        Optional<StudentEntity> optional = studentRepository.findFirstByName(name);
        if(optional.isEmpty()){
            throw  new AppBadException("Not found");
        }
        StudentEntity entity = optional.get();

        StudentDTO dto = toDto(entity);

        return dto;
    }

//    public List<StudentDTO> getStudents(Pageable pageable) {
//        Page<StudentEntity> all = studentRepository.findAll(pageable);
//
//
//        for (StudentEntity entity : all) {
//            toDto(entity)
//        }
//    }

//    public List<StudentDTO> getStudents(Integer page, Integer size) {
//
//        Pageable pageable= PageRequest.of(page,size );
//
//        Page<StudentEntity> all = studentRepository.findAll(pageable);
//
//        List<StudentEntity> content = all.getContent();
//
//        List<StudentDTO> dtoList=new LinkedList<>();
//
//        for (StudentEntity entity : content) {
//            dtoList.add(toDto(entity));
//        }
//        return dtoList;
//    }


    public Long countName(String name) {
        return studentRepository.countByNameIgnoreCase(name);
    }

    public List<StudentDTO> getStudents(Pageable pageable) {

        Page<StudentEntity> all = studentRepository.findAllBy(pageable);

        List<StudentDTO> dtoList=new LinkedList<>();

        for (StudentEntity entity : all) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

//    public List<StudentDTO> paginationByLevel(Pageable pageable, Integer level) {
//        Page<StudentEntity> allByLevel = studentRepository.findAllByLevel(pageable, level);
//
//        List<StudentDTO> dtoList = new LinkedList<>();
//        for (StudentEntity entity : allByLevel) {
//            dtoList.add(toDto(entity));
//        }
//        return dtoList;
//    }

    public PageImpl<StudentDTO> paginationByGender(Integer page, Integer size, Gender gender) {

        Sort sort=Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page-1, size,sort);

        Page<StudentEntity> allByGender = studentRepository.findAllByGender(pageable, gender);

        List<StudentEntity> content = allByGender.getContent();
        long totalElements = allByGender.getTotalElements();

        List<StudentDTO>  dtoList =new LinkedList<>();

        for (StudentEntity entity : content) {
            dtoList.add(toDto(entity));
        }
        return new PageImpl<>(dtoList,pageable,totalElements);

    }


    public List<StudentDTO> findByCreatedDate(LocalDate localDate) {

        LocalDateTime fromDate=LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime toDate=LocalDateTime.of(localDate, LocalTime.MAX);

        List<StudentEntity> allByCreatedDateBetween = studentRepository.findAllByCreatedDateBetween(fromDate, toDate);

        List<StudentDTO> dtoList=new LinkedList<>();

        for (StudentEntity entity : allByCreatedDateBetween) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

    public PageImpl<StudentDTO> pagination(Integer page, Integer size) {

        Sort sort=Sort.by(Sort.Direction.DESC,"createdDate");

        Pageable pageable=PageRequest.of(page-1, size,sort);

        Page<StudentEntity> allBy = studentRepository.findAllBy(pageable);

        List<StudentEntity> content = allBy.getContent();
        long totalElements = allBy.getTotalElements();

        List<StudentDTO>  dtoList=new LinkedList<>();

        for (StudentEntity entity : content) {
            dtoList.add(toDto(entity));
        }

        return new PageImpl<>(dtoList,pageable,totalElements);
//        PaginationResultDTO<StudentDTO> paginationResultDTO=new PaginationResultDTO<>();
//
//        paginationResultDTO.setList(dtoList);
//        paginationResultDTO.setTotalSize(totalElements);
//        return paginationResultDTO;

    }

    public PageImpl<StudentDTO> paginationByLevel(Integer page, Integer size,  Integer level) {
        Sort sort=Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable pageable = PageRequest.of(page-1, size, sort);

        Page<StudentEntity> allByLevel = studentRepository.findAllByLevel(pageable, level);
        List<StudentEntity> content = allByLevel.getContent();

        long totalElements = allByLevel.getTotalElements();

        List<StudentDTO> dtoList=new LinkedList<>();
        for (StudentEntity entity : content) {
            dtoList.add(toDto(entity));
        }

        return new PageImpl<>(dtoList,pageable,totalElements);
    }

    public List<StudentDTO> getAllByWithSort() {
        studentRepository.findAllByName("Dybala",Sort.by(Sort.Direction.DESC,"name"));
//        studentRepository.findAll(Sort.by((Sort.Direction.DESC,"surnmae"));

        return null;
    }

    public StudentDTO findById(Integer studentID) {
        Optional<StudentEntity> byId = studentRepository.findByStudentId(studentID);

        if(byId.isEmpty()){
            throw new AppBadException("Student not found");
        }


        StudentEntity studentEntity = byId.get();
        StudentDTO dto = toDto(studentEntity);

        return dto;

    }

//    public void testCustomQuery(String name){
//        studentRepository.findbyName1(name);
//    }
//
//    public void test(){
//        studentRepository.findByNameWithSort("ali", "aliyev", Sort.by(Sort.Direction.DESC, "age"));
//    }

    public void test(){
//        List<Object[]> objectList = studentRepository.getShortInfo();
//        List<StudentDTO> dtoList = new LinkedList<>();
//        for (Object[] obj : objectList) {
//            StudentDTO dto = new StudentDTO();
//            dto.setId((Integer) obj[0]);
//            dto.setName((String) obj[1]);
//            dto.setSurname((String) obj[2]);
//            dtoList.add(dto);
//        }

        List<StudentMapper> studentMappers = studentRepository.joinExample12();


    }

    public boolean update2 (Integer id, StudentDTO dto){
        int i = studentRepository.updateStudent(dto.getName(), dto.getSurname(), id);

        return true;
    }


    public PageImpl<StudentDTO> filter (StudentFilterDto filterDto, Integer page, Integer size){


        Pageable pageable=PageRequest.of(page, size);
        PaginationResultDTO<StudentEntity> entityList =
                studentCustomRepository.filter2(filterDto, page, size);
        List<StudentDTO>  dtoList=new LinkedList<>();
        for (StudentEntity studentEntity : entityList.getList()) {
            dtoList.add(toDto(studentEntity));
        }

        return new PageImpl<>(dtoList, pageable, entityList.getTotalSize());

    }
}
