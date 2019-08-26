package com.hrd.somchbab.rest_controller;

import com.hrd.somchbab.repository.model.Classenroll;
import com.hrd.somchbab.service.classenroll_service.ClassenrollService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/classenroll")
public class ClassenrollRestController {

    @Autowired
    ClassenrollService classenrollService;

    @PostMapping(value = "enroll")
    public Map<String, Object> enrollStudent(@RequestBody final Classenroll classenroll){
        Integer classenrollAssigned = classenrollService.findIdByUserIdAndClassroomId(classenroll.getUser().getId(), classenroll.getClassroom().getId());
        Map<String, Object> classenrollMap = new HashMap<>();
        boolean enrolledStatus = false;
        if (classenrollAssigned == null){
            classenrollService.enrollStudent(classenroll);
            enrolledStatus = true;
        }
        classenrollMap.put("status", enrolledStatus);
        return classenrollMap;
    }
}
