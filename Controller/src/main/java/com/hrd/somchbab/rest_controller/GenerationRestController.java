package com.hrd.somchbab.rest_controller;

import com.hrd.somchbab.repository.model.Course;
import com.hrd.somchbab.service.course_service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/generation")
public class GenerationRestController {

    @Autowired
    CourseService courseService;

    @GetMapping(value = "{id}")
    public Map<String, Object> getCoursesById(@PathVariable("id") int id){
        Map<String, Object> courseMap = new HashMap<>();
        List<Course> courses = courseService.findByGenerationId(id);
        courseMap.put("data", courses);
        courseMap.put("status", courses != null);
        return courseMap;
    }

    @GetMapping(value = "{generationId}/student/{userId}")
    public Map<String, Object> getCoursesByStudentId(@PathVariable("generationId") int generationId, @PathVariable("userId") int userId){
        Map<String, Object> courseMapOne = new HashMap<>();
        List<Course> coursesOne = courseService.findByStudentId(generationId, userId);
        courseMapOne.put("data", coursesOne);
        courseMapOne.put("status", coursesOne != null);
        return courseMapOne;
    }
}
