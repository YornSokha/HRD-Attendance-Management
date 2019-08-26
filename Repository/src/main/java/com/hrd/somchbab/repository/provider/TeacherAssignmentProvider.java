package com.hrd.somchbab.repository.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class TeacherAssignmentProvider {

    public String findIdByUserIdAndClassroomId(@Param("userId") int userId, @Param("classroomID") int classroomId){
        return new SQL(){{
            SELECT("ta.id");
            FROM("sc_teacher_assignments ta");
            INNER_JOIN("sc_users u on u.id = ta.user_id");
            INNER_JOIN("sc_classrooms c on c.id = ta.classroom_id");
            WHERE("c.id = " + classroomId + " AND u.id = " + userId);
        }}.toString();
    }
}
