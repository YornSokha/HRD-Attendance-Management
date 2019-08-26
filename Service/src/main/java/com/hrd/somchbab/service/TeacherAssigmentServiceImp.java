package com.hrd.somchbab.service;

import com.hrd.somchbab.repository.model.TeacherAssignment;
import com.hrd.somchbab.repository.teacher_assignment_repository.TeacherAssignmentRepository;
import com.hrd.somchbab.service.teacher_assignment_service.TeacherAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherAssigmentServiceImp implements TeacherAssignmentService {

    @Autowired
    TeacherAssignmentRepository teacherAssignmentRepository;

    @Override
    public Integer findIdByUserIdAndClassroomId(int userId, int classroomId) {
        return teacherAssignmentRepository.findIdByUserIdAndClassroomId(userId, classroomId);
    }

    @Override
    public void assignTeacher(TeacherAssignment teacherAssignment) {
        teacherAssignmentRepository.assignTeacher(teacherAssignment);
    }
}
