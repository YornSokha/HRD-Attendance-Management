package com.hrd.somchbab.repository.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class AcademicProvider {
    public String findAllByStudentID(@Param("id") int id) {
        return new SQL(){{
            SELECT("sc_academics.id, sc_academics.name, sc_academics.status");
            FROM("sc_academics");
            INNER_JOIN("sc_generations ON sc_generations.academic_id = sc_academics.id");
            INNER_JOIN("sc_courses ON sc_courses.generation_id = sc_generations.id");
            INNER_JOIN("sc_classrooms ON sc_classrooms.course_id = sc_courses.id");
            INNER_JOIN("sc_classenrolls ON sc_classenrolls.classroom_id = sc_classrooms.id");
            INNER_JOIN("sc_users ON sc_classenrolls.user_id = sc_users.id");
            WHERE("sc_users.id = #{id}");
            AND();
            WHERE("sc_users.status = TRUE");
            AND();
            WHERE("sc_users.deleted = FALSE");
            GROUP_BY("sc_academics.id");
        }}.toString();
    }
}
