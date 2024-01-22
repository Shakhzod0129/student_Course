package com.example.student_course.controller;

import com.example.student_course.dto.StudentCourseMarkDTO;
import com.example.student_course.dto.StudentCourseMarkFilterDto;
import com.example.student_course.dto.StudentDTO;
import com.example.student_course.dto.StudentFilterDto;
import com.example.student_course.service.StudentCourseMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student_course_mark")
public class StudentCourseMarkController {
    @Autowired
    private StudentCourseMarkService studentCourseMarkService;

    @PostMapping("")  //  POST /student
    public ResponseEntity<?> create(@RequestBody StudentCourseMarkDTO studentCourseMarkDto) {
        StudentCourseMarkDTO result = studentCourseMarkService.create(studentCourseMarkDto);
        return ResponseEntity.ok(result); // 200
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>  update(@PathVariable (name = "id") Integer id,
                                     @RequestBody StudentCourseMarkDTO dto){
        return ResponseEntity.ok(studentCourseMarkService.update(id,dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCourseMarkDTO> getById(@PathVariable (name = "id") Integer id){
        return ResponseEntity.ok(studentCourseMarkService.getById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<StudentCourseMarkDTO>> getAll(){
        return ResponseEntity.ok(studentCourseMarkService.getAll());
    }

    @GetMapping("/getWithDetail/{id}")
    public ResponseEntity<StudentCourseMarkDTO> getWithDetail(@PathVariable Integer id){
        return ResponseEntity.ok(studentCourseMarkService.getWithDetail(id));
    }


    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<StudentCourseMarkDTO>> pagination(@RequestParam int page,
                                                                     @RequestParam int size) {
        return ResponseEntity.ok(studentCourseMarkService.pagination(page,size));
    }

    @GetMapping("/paginationByStudentId")
    public ResponseEntity<PageImpl<StudentCourseMarkDTO>> paginationByStudentId(@RequestParam int page,
                                                                     @RequestParam int size,
                                                                                @RequestParam Integer studentId) {
        return ResponseEntity.ok(studentCourseMarkService.paginationByStudentId(page,size,studentId));
    }

    @GetMapping("/paginationByCourseId")
    public ResponseEntity<PageImpl<StudentCourseMarkDTO>> paginationByCourseId(@RequestParam int page,
                                                                                @RequestParam int size,
                                                                                @RequestParam Integer courseId) {
        return ResponseEntity.ok(studentCourseMarkService.paginationByCourseId(page,size,courseId));
    }

    @GetMapping("/getMarkByCourse")
    public ResponseEntity<List<StudentCourseMarkDTO>> getMarkByCourse(@RequestParam Integer studentId,
                                                                      @RequestParam Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getMarkByCourse(studentId,courseId));
    }

    @GetMapping("/getLastMarkAndCourse")
    public ResponseEntity<List<StudentCourseMarkDTO>> getLastMarkAndCourse(@RequestParam Integer studentID){
        return ResponseEntity.ok(studentCourseMarkService.getLastMarkAndCourse(studentID));
    }

    @GetMapping("/getTop3Mark")
    public ResponseEntity<List<StudentCourseMarkDTO>> getTop3Mark(@RequestParam Integer studentID){
        return ResponseEntity.ok(studentCourseMarkService.getTop3Mark(studentID));
    }

    @GetMapping("/getFirstMarkByStudent")
    public ResponseEntity<List<StudentCourseMarkDTO>> getFirstMarkByStudent(@RequestParam Integer studentID){
        return ResponseEntity.ok(studentCourseMarkService.getFirstMarkByStudent(studentID));
    }

    @GetMapping("/getFirstMarkFromCourse")
    public ResponseEntity<List<StudentCourseMarkDTO>> getFirstMarkFromCourse(@RequestParam Integer studentID,
                                                                             @RequestParam Integer courseID){
        return ResponseEntity.ok(studentCourseMarkService.getFirstMarkFromCourse(studentID,courseID));
    }

    @GetMapping("/getHighestMarkFromCourse")
    public ResponseEntity<List<StudentCourseMarkDTO>> getHighestMarkFromCourse(@RequestParam Integer studentID,
                                                                             @RequestParam Integer courseID){
        return ResponseEntity.ok(studentCourseMarkService.getHighestMarkFromCourse(studentID,courseID));
    }

    @GetMapping("/getAverageMark")
    public ResponseEntity<Double> getAverageMark(@RequestParam Integer studentID){
        return ResponseEntity.ok(studentCourseMarkService.getAverageMark(studentID));
    }

    @GetMapping("/getAverageMarkFromCourse")
    public ResponseEntity<Double> getAverageMarkFromCourse(@RequestParam Integer studentID,
                                                           @RequestParam Integer courseID){
        return ResponseEntity.ok(studentCourseMarkService.getAverageMarkFromCourse(studentID,courseID));
    }

    @GetMapping("/getHighMarksThanGivenMark")
    public ResponseEntity<List<Double>> getHighMarksThanGivenMark(@RequestParam Integer studentID,
                                                           @RequestParam Integer mark){
        return ResponseEntity.ok(studentCourseMarkService.getHighMarksThanGivenMark(studentID,mark));
    }

    @GetMapping("/getHighMarksGivenCourse")
    public ResponseEntity<Double> getHighMarksGivenCourse(@RequestParam Integer courseID){
        return ResponseEntity.ok(studentCourseMarkService.getHighMarksGivenCourse(courseID));
    }

    @GetMapping("/getAverageMarkGivenCourse")
    public ResponseEntity<Double> getAverageMarkGivenCourse(@RequestParam Integer courseID){
        return ResponseEntity.ok(studentCourseMarkService.getAverageMarkGivenCourse(courseID));
    }
    @GetMapping("/getCountMarkByGivenCourse")
    public ResponseEntity<Integer> getCountMarkByGivenCourse(@RequestParam Integer courseID){
        return ResponseEntity.ok(studentCourseMarkService.getCountMarkByGivenCourse(courseID));
    }

    @GetMapping("/getMarksByStudentId")
    public ResponseEntity<?> getMarksByStudentId(@RequestParam Integer studentID){
        return ResponseEntity.ok(studentCourseMarkService.getMarksByStudentId(studentID));
    }

    @GetMapping("/25")
    public ResponseEntity<?>getByStudentIdMarkList(@RequestParam("studentId")Integer id){
        return ResponseEntity.ok(studentCourseMarkService.getByStudentIdMarkList(id));
    }

    @GetMapping("/26")
    public ResponseEntity<?>getByCourseIdMarkList(@RequestParam("courseId")Integer id){
        return ResponseEntity.ok(studentCourseMarkService.getByCourseIdMarkList(id));
    }

    @GetMapping("/27")
    public ResponseEntity<List<StudentCourseMarkDTO>>getAllMarkList(){
        return ResponseEntity.ok(studentCourseMarkService.getAllMarkList());
    }


    @PostMapping("/filter")
    public ResponseEntity<PageImpl<StudentCourseMarkDTO>> create(@RequestBody StudentCourseMarkFilterDto dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageImpl<StudentCourseMarkDTO> result = studentCourseMarkService.filter(dto, page, size);
        return ResponseEntity.ok(result);
    }




}
