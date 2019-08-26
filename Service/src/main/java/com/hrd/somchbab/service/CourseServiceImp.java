package com.hrd.somchbab.service;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.course_repository.CourseRepository;
import com.hrd.somchbab.repository.model.Course;
import com.hrd.somchbab.service.course_service.CourseService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImp implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public void add(Course course) {
        courseRepository.add(course);
    }

    @Override
    public void update(Course course) {
        courseRepository.update(course);
    }

    @Override
    public Course find(int id) {
        return courseRepository.find(id);
    }

    @Override
    public void delete(int id) {
        courseRepository.delete(id);
    }


    public List<Course> findByGenerationId(int id) {
        return courseRepository.findByGenerationId(id);
    }

    @Override
    public List<Course> findByStudentId(@Param("generationId") int generationId, @Param("userId") int userId) {
        return courseRepository.findByStudentId(generationId, userId);
    }

    @Override
    public int getLastId() {
        return courseRepository.getLastId();
    }

    @Override
    public List<Course> filterCoursesByNameAndGenerationId(String fullName, int id, Paging paging) {
        return courseRepository.filterCoursesByNameAndGenerationId(fullName,id,paging);
    }

    @Override
    public List<Course> filterCoursesByNameAndAcademicId(String fullName, int id, Paging paging) {
        return courseRepository.filterCoursesByNameAndAcademicId(fullName,id,paging);
    }

    @Override
    public List<Course> filterCoursesByGenerationId(int id, Paging paging) {
        return courseRepository.filterCoursesByGenerationId(id,paging);
    }

    @Override
    public List<Course> filterCoursesByAcademicId(int id, Paging paging) {
        return courseRepository.filterCoursesByAcademicId(id,paging);
    }

    @Override
    public List<Course> findAllCoursesByPaging(Paging paging) {
        return courseRepository.findAllCoursesByPaging(paging);
    }

    @Override
    public List<Course> findAllCourses() {
        return courseRepository.findAllCourses();
    }

    @Override
    public List<Course> findCoursesByName(String fullName, Paging paging) {
        return courseRepository.findCoursesByName(fullName,paging);
    }

    @Override
    public List<Course> findByName(String name) {
        return  courseRepository.findByName(name);
    }
}
