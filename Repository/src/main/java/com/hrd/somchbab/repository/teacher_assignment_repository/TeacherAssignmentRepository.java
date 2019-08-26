package com.hrd.somchbab.repository.teacher_assignment_repository;

import com.hrd.somchbab.repository.model.TeacherAssignment;
import com.hrd.somchbab.repository.provider.TeacherAssignmentProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherAssignmentRepository {
    @SelectProvider(method = "findIdByUserIdAndClassroomId", type = TeacherAssignmentProvider.class)
    Integer findIdByUserIdAndClassroomId(@Param("userId") int userId, @Param("classroomID") int classroomId);

    @Insert("INSERT INTO sc_teacher_assignments(user_id, classroom_id, class_teacher) VALUES(#{user.id}, #{classroom.id}, #{classTeacher})")
    void assignTeacher(TeacherAssignment teacherAssignment);
}
