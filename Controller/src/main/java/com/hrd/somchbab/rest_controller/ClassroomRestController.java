package com.hrd.somchbab.rest_controller;

import com.hrd.somchbab.repository.model.Classroom;
import com.hrd.somchbab.service.classroom_service.ClassroomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/classroom")
public class ClassroomRestController {

    @Autowired
    ClassroomService classroomService;

    @GetMapping(value = "{id}")
    public Map<String, Object> getClassnameById(@PathVariable("id") int id){
        Map<String, Object> classroomMap = new HashMap<>();
        List<Classroom> classrooms = classroomService.findClassroomWithNameByCourseId(id);
        classroomMap.put("data", classrooms);
        classroomMap.put("status", classrooms != null);
        return classroomMap;
    }
}
