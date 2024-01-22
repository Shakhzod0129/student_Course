package com.example.student_course.service;

import com.example.student_course.dto.CourseDTO;
import com.example.student_course.dto.CourseFilterDto;
import com.example.student_course.dto.PaginationResultDTO;
import com.example.student_course.dto.StudentDTO;
import com.example.student_course.entity.CourseEntity;
import com.example.student_course.exp.AppBadException;
import com.example.student_course.repository.CourseCustomRepository;
import com.example.student_course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseCustomRepository courseCustomRepository;
    public CourseDTO create(CourseDTO dto) {
        CourseEntity entity=new CourseEntity();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        entity.setCreatedDate(LocalDateTime.now());
        courseRepository.save(entity);

        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }

    public List<CourseDTO> getAll() {
        Iterable<CourseEntity> all = courseRepository.findAll();

        List<CourseDTO> dtoList=new LinkedList<>();
        for (CourseEntity entity : all) {
            dtoList.add(toDto(entity));
        }

        return dtoList;

    }

    public CourseDTO getById(Integer id) {

        Optional<CourseEntity> optional = courseRepository.findById(id);

        if(optional.isEmpty()){
            throw new AppBadException("Course has not found with this id❌");
        }

        CourseEntity entity = optional.get();

        return toDto(entity);

    }

    public Boolean update(Integer id, CourseDTO dto) {
        Optional<CourseEntity> optional = courseRepository.findById(id);

        if(optional.isEmpty()){
            throw new AppBadException("Course has not found with this id❌");
        }

        CourseEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        courseRepository.save(entity);

        return true;

    }

    public Boolean delete(Integer id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);

        if(optional.isEmpty()){
            throw new AppBadException("Course has not found with this id❌");
        }

        courseRepository.delete(optional.get());
        return true;

    }

    public List<CourseDTO> findByName(String name) {

        List<CourseEntity> studentEntity=courseRepository.findByName(name);

        List<CourseDTO> dtoList=new LinkedList<>();

        for (CourseEntity entity : studentEntity) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }





    public List<CourseDTO> findByPrice(Double price) {
        List<CourseEntity> studentEntity=courseRepository.findByPrice(price);

        List<CourseDTO> dtoList=new LinkedList<>();

        for (CourseEntity entity : studentEntity) {
            dtoList.add(toDto(entity));
        }
        return dtoList;

    }

    public List<CourseDTO> findByDuration(Integer duration) {
        List<CourseEntity> studentEntity=courseRepository.findByDuration(duration);

        List<CourseDTO> dtoList=new LinkedList<>();

        for (CourseEntity entity : studentEntity) {
            dtoList.add(toDto(entity));
        }
        return dtoList;

    }

    public List<CourseDTO> getBetweenTwoPrices(Double n1, Double n2) {

        List<CourseEntity> entities=courseRepository.findByPriceBetween(n1,n2);

        List<CourseDTO> dtoList=new LinkedList<>();

        for (CourseEntity entity : entities) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

    public List<CourseDTO> getByBetweenTwoDates(LocalDateTime date1, LocalDateTime date2) {

        List<CourseEntity> entities=courseRepository.findByCreatedDateBetween(date1,date2);

        List<CourseDTO>  dtoList=new LinkedList<>();
        for (CourseEntity entity : entities) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }


    public CourseDTO toDto(CourseEntity entity){
        CourseDTO dto=new CourseDTO();

        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }

    public CourseEntity get(Integer id){
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        return optional.get();
    }

    public PageImpl<CourseDTO> paginatiion(Integer page , Integer size) {

        Sort sort=Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable pageable = PageRequest.of(page-1, size, sort);
        Page<CourseEntity> all = courseRepository.findAll(pageable);

        List<CourseEntity> content = all.getContent();
        long totalElements = all.getTotalElements();

        List<CourseDTO> dtoList=new LinkedList<>();

        for (CourseEntity courseEntity : content) {
            dtoList.add(toDto(courseEntity));
        }

        return new PageImpl<>(dtoList,pageable,totalElements);
    }

    public List<CourseDTO> paginatiionByCreatedDate(Pageable pageable) {

        Page<CourseEntity> allByCreatedDate = courseRepository.findAllByOrderByCreatedDateDesc(pageable);

        List<CourseDTO> dtoList=new LinkedList<>();

        for (CourseEntity courseEntity : allByCreatedDate) {
            dtoList.add(toDto(courseEntity));
        }
        return dtoList;
    }

    public PageImpl<CourseDTO> paginatiionByPrice(Integer page, Integer size, Double price) {
        Sort sort=Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable pageable = PageRequest.of(page-1, size, sort);
        Page<CourseEntity> allByPrice = courseRepository.findAllByPrice(pageable, price);

        List<CourseEntity> content = allByPrice.getContent();
        long totalElements = allByPrice.getTotalElements();

        List<CourseDTO> dtoList=new LinkedList<>();

        for (CourseEntity courseEntity : content) {
            dtoList.add(toDto(courseEntity));
        }

        return new PageImpl<>(dtoList,pageable,totalElements);

    }

    public PageImpl<CourseDTO> paginatiionByBetweenTwoPrice(Integer page,Integer size, Double price1, Double price2) {

        Sort sort=Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable pageable=PageRequest.of(page-1,size,sort);
        Page<CourseEntity> allByPriceBetween = courseRepository.findAllByPriceBetween(pageable, price1, price2);
        List<CourseEntity> content = allByPriceBetween.getContent();
        long totalElements = allByPriceBetween.getTotalElements();
        int totalPages = allByPriceBetween.getTotalPages();

        List<CourseDTO> dtoList=new LinkedList<>();

        for (CourseEntity courseEntity : content) {
            dtoList.add(toDto(courseEntity));
        }

        return new PageImpl<>(dtoList,pageable,totalPages);
    }

    public PageImpl<CourseDTO> filter(CourseFilterDto dto, Integer page, Integer size) {
        Pageable pageable=PageRequest.of(page-1,size);
        PaginationResultDTO<CourseEntity> list = courseCustomRepository.filter(dto, page, size);

        Long totalSize = list.getTotalSize();
        List<CourseDTO> dtoList=new LinkedList<>();

        for (CourseEntity courseEntity : list.getList()) {
            dtoList.add(toDto(courseEntity));
        }

        return new PageImpl<>(dtoList,pageable,totalSize);
    }
}
