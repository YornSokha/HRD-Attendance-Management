package com.hrd.somchbab.service.teacher_assignment_service;

import com.hrd.somchbab.repository.model.TeacherAssignment;

public interface TeacherAssignmentService {
    Integer findIdByUserIdAndClassroomId(int userId, int classroomId);
    void assignTeacher(TeacherAssignment teacherAssignment);
}
