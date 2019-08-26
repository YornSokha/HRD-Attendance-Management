package com.hrd.somchbab.rest_controller;

import com.hrd.somchbab.repository.model.TeacherAssignment;
import com.hrd.somchbab.service.teacher_assignment_service.TeacherAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/teacher-assignment")
public class TeacherAssignmentRestController {

    @Autowired
    private TeacherAssignmentService teacherAssignmentService;

    @PostMapping(value = "assign")
    public Map<String, Object> enrollStudent(@RequestBody final TeacherAssignment teacherAssignment){
        Integer teacherAssignmentAssigned = teacherAssignmentService.findIdByUserIdAndClassroomId(teacherAssignment.getUser().getId(), teacherAssignment.getClassroom().getId());
        Map<String, Object> teacherAssignmentMap = new HashMap<>();
        boolean assignedStatus = false;
        if (teacherAssignmentAssigned == null){
            teacherAssignmentService.assignTeacher(teacherAssignment);
            assignedStatus = true;
        }
        teacherAssignmentMap.put("status", assignedStatus);
        return teacherAssignmentMap;
    }
}
