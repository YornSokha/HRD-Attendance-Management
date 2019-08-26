package com.hrd.somchbab.service.course_service;


import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.model.Course;

import com.hrd.somchbab.repository.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.security.KeyStore;
import java.util.List;
@Service
public interface CourseService {
    List<Course> findAll();
    void add(Course course);
    void update(Course course);
    Course find(int id);
    void delete(int id);
    List<Course> findByName(String name);
    List<Course> findByGenerationId(int id);
    List<Course> findByStudentId(@Param("generationId") int generationId, @Param("userId") int userId);
    int getLastId();


    List<Course> filterCoursesByNameAndGenerationId(String fullName, int id, Paging paging);

    List<Course> filterCoursesByNameAndAcademicId(String fullName, int id, Paging paging);

    List<Course> filterCoursesByGenerationId(int id, Paging paging);

    List<Course> filterCoursesByAcademicId(int id, Paging paging);

    List<Course> findAllCoursesByPaging(Paging paging);

    List<Course> findAllCourses();

    List<Course> findCoursesByName(String fullName, Paging paging);
}
