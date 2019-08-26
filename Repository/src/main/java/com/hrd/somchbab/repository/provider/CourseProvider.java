package com.hrd.somchbab.repository.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class CourseProvider {
    public String findByStudentId(@Param("generationId") int generationId, @Param("userId") int userId) {
        return new SQL(){{
            SELECT("sc_courses.id, sc_courses.name, sc_courses.generation_id, sc_courses.status, sc_courses.deleted");
            FROM("sc_courses");
            INNER_JOIN("sc_classrooms ON sc_classrooms.course_id = sc_courses.id");
            INNER_JOIN("sc_classenrolls ON sc_classenrolls.classroom_id = sc_classrooms.id");
            INNER_JOIN("sc_users ON sc_classenrolls.user_id = sc_users.id");
            WHERE("sc_courses.generation_id = #{generationId}");
            AND();
            WHERE("sc_users.id = #{userId}");
            AND();
            WHERE("sc_users.status = 't'");
            AND();
            WHERE("sc_users.deleted = 'f'");
            GROUP_BY("sc_courses.id");
        }}.toString();
    }
}
