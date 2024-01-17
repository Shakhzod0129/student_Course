package com.example.student_course.controller;

import com.example.student_course.dto.CourseDTO;
import com.example.student_course.dto.CourseDTO;
import com.example.student_course.dto.StudentDTO;
import com.example.student_course.enums.Gender;
import com.example.student_course.exp.AppBadException;
import com.example.student_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("")  //  POST /student
    public ResponseEntity<?> create(@RequestBody CourseDTO dto) {
        CourseDTO result = courseService.create(dto);
        return ResponseEntity.ok(result); // 200
    }

    @GetMapping("")
    public ResponseEntity<List<CourseDTO>> getAll(){
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getByID(@PathVariable (name = "id") Integer id){
        return ResponseEntity.ok(courseService.getById(id));
    }
//
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateById(@PathVariable (name = "id") Integer id,
                                              @RequestBody CourseDTO dto){
        return ResponseEntity.ok(courseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable (name = "id") Integer id){
        return ResponseEntity.ok(courseService.delete(id));
    }

    @GetMapping("/byName")
    public ResponseEntity<List<CourseDTO>> getByName(@RequestParam (name = "name") String name){
        return ResponseEntity.ok(courseService.findByName(name));
    }

    @GetMapping("/byPrice")
    public ResponseEntity<List<CourseDTO>> getByPrice(@RequestParam (name = "price") Double price){
        return ResponseEntity.ok(courseService.findByPrice(price));
    }

    @GetMapping("/byDuration")
    public ResponseEntity<List<CourseDTO>> getByDuration(@RequestParam (name = "duration") Integer duration){
        return ResponseEntity.ok(courseService.findByDuration(duration));
    }

    @GetMapping("/byBetweenTwoPrices")
    public ResponseEntity<List<CourseDTO>> getBetweenTwoPrices(@RequestParam (name = "price1") Double n1,
                                                               @RequestParam (name = "price2") Double n2){
        return ResponseEntity.ok(courseService.getBetweenTwoPrices(n1,n2));
    }

    @GetMapping("/byBetweenTwoDates")
    public ResponseEntity<List<CourseDTO>> getByBetweenTwoDates(@RequestParam(name = "date1")LocalDateTime date1,
                                                                @RequestParam(name = "date2") LocalDateTime date2){
        return ResponseEntity.ok(courseService.getByBetweenTwoDates(date1,date2));
    }


    @GetMapping("/paginatiion")
    public ResponseEntity<PageImpl<CourseDTO>> paginatiion(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(courseService.paginatiion(page, size));
    }

    @GetMapping("/paginatiionByCreatedDate")
    public ResponseEntity<List<CourseDTO>> paginatiionByCreatedDate(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(courseService.paginatiionByCreatedDate(pageable));
    }

    @GetMapping("/paginatiionByPrice")
    public ResponseEntity<PageImpl<CourseDTO>> paginatiionByPrice(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(name = "price") Double price) {
        return ResponseEntity.ok(courseService.paginatiionByPrice(page,size,price));
    }

    @GetMapping("/paginatiionByBetweenTwoPrice")
    public ResponseEntity<PageImpl<CourseDTO>> paginatiionByBetweenTwoPrice(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(name = "price1") Double price1,
                                                                        @RequestParam (name = "price2") Double price2) {
        return ResponseEntity.ok(courseService.paginatiionByBetweenTwoPrice(page,size,price1,price2));
    }














}
