package com.example.student_course.controller;


import com.example.student_course.dto.PaginationResultDTO;
import com.example.student_course.dto.StudentDTO;
import com.example.student_course.dto.StudentFilterDto;
import com.example.student_course.enums.Gender;
import com.example.student_course.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    //    @PostMapping("")
    @PostMapping("")  //  POST /student
    public ResponseEntity<?> create(@RequestBody StudentDTO dto) {
        StudentDTO result = studentService.create(dto);
        return ResponseEntity.ok(result); // 200
    }

    @GetMapping("")
    public ResponseEntity<List<StudentDTO>> getAll(){
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getByID(@PathVariable (name = "id") Integer id){
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateById(@PathVariable (name = "id") Integer id,
                                        @RequestBody StudentDTO dto){
        return ResponseEntity.ok(studentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable (name = "id") Integer id){
        return ResponseEntity.ok(studentService.delete(id));
    }

    @GetMapping("/byName")
    public ResponseEntity<List<StudentDTO>> getByName(@RequestParam (name = "name") String name){
        return ResponseEntity.ok(studentService.findByName(name));
    }

    @GetMapping("/bySurname")
    public ResponseEntity<List<StudentDTO>> getBySurname(@RequestParam (name = "surname") String surname){
        return ResponseEntity.ok(studentService.findBySurname(surname));
    }

    @GetMapping("/byLevel")
    public ResponseEntity<List<StudentDTO>> getByLevel(@RequestParam (name = "level") Integer level){
        return ResponseEntity.ok(studentService.findByLevel(level));
    }

    @GetMapping("/byAge")
    public ResponseEntity<List<StudentDTO>> getByAge(@RequestParam (name = "age") Integer age){
        return ResponseEntity.ok(studentService.findByAge(age));
    }

    @GetMapping("/byGender")
    public ResponseEntity<List<StudentDTO>> getByGender(@RequestParam (name = "gender") Gender gender){
        return ResponseEntity.ok(studentService.findByGender(gender));
    }

    @GetMapping("/firstByName")
    public ResponseEntity<StudentDTO> firstByName(@RequestParam (name = "name")String name){
        return ResponseEntity.ok(studentService.firtByName(name));
    }

//    @GetMapping("/page")
//    public ResponseEntity<List<StudentDTO>> getStudents(@RequestParam("page") Integer page,
//                                                        @RequestParam("size") Integer size) {
//        return ResponseEntity.ok(studentService.getStudents(page,size));
//    }

    @GetMapping("/countName")
    public ResponseEntity<Long> countName(@RequestParam (name = "name") String name){
        return ResponseEntity.ok(studentService.countName(name));
    }


    @GetMapping("/pagination1")
    public ResponseEntity<List<StudentDTO>> getStudents(@RequestParam int page,
                                        @RequestParam int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(studentService.getStudents(pageable));
    }

    @GetMapping("/pagination2")
    public ResponseEntity<PageImpl<StudentDTO>> pagination(@RequestParam("page") Integer page,
                                                           @RequestParam("size") Integer size) {

        return ResponseEntity.ok(studentService.pagination(page, size));
    }


    @GetMapping("/paginatiionByLevel")
    public PageImpl<StudentDTO> paginatiionByLevel(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                               @RequestParam (name = "level") Integer level) {
        return studentService.paginationByLevel(page,size,level);
    }

    @GetMapping("/paginatiionByGender")
    public PageImpl<StudentDTO> paginatiionByGender(@RequestParam int page,
                                               @RequestParam int size,
                                               @RequestParam (name = "gender") Gender gender) {
        return studentService.paginationByGender(page,size,gender);
    }

    @GetMapping("/findByCreatedDate")
    public ResponseEntity<List<StudentDTO>> findByCreatedDate(@RequestParam LocalDate localDate){
        return ResponseEntity.ok(studentService.findByCreatedDate(localDate));
    }

//    @GetMapping("")
//    public ResponseEntity<List<StudentDTO>> getAllByWithSort(){
//        return ResponseEntity.of(studentService.getAllByWithSort());
//    }


    @GetMapping("findById/{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok(studentService.findById(id));
    }


    @PostMapping("/filter")
    public ResponseEntity<PageImpl<StudentDTO>> create(@RequestBody StudentFilterDto dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageImpl<StudentDTO> result = studentService.filter(dto, page, size);
        return ResponseEntity.ok(result);
    }






}

