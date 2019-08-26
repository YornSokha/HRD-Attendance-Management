package com.hrd.somchbab.repository.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class GenerationProvider {
    public String findByStudentId(@Param("academicId") int academicId, @Param("userId") int userId) {
        return new SQL(){{
            SELECT("sc_generations.id, sc_generations.name, sc_generations.status");
            FROM("sc_generations");
            INNER_JOIN("sc_courses ON sc_courses.generation_id = sc_generations.id");
            INNER_JOIN("sc_classrooms ON sc_classrooms.course_id = sc_courses.id");
            INNER_JOIN("sc_classenrolls ON sc_classenrolls.classroom_id = sc_classrooms.id");
            INNER_JOIN("sc_users ON sc_classenrolls.user_id = sc_users.id");
            WHERE("sc_generations.academic_id = #{academicId}");
            AND();
            WHERE("sc_users.id = #{userId}");
            AND();
            WHERE("sc_users.status = 't'");
            AND();
            WHERE("sc_users.deleted = 'f'");
            GROUP_BY("sc_generations.id");
        }}.toString();
    }

    public String findByClassroomId(@Param("id") int id) {
        return new SQL(){{
            SELECT("sc_generations.ID, sc_generations.NAME, sc_generations.academic_id, sc_generations.status");
            FROM("sc_generations");
            INNER_JOIN("sc_courses ON sc_courses.generation_id = sc_generations.ID");
            INNER_JOIN("sc_classrooms ON sc_classrooms.course_id = sc_courses.id");
            WHERE("sc_classrooms.id = #{id}");
            AND();
            WHERE("sc_classrooms.status = 't'");
            AND();
            WHERE("sc_classrooms.deleted = 'f'");
        }}.toString();
    }
}
