package com.hrd.somchbab.repository.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ReportProvider {
    public String findAll(@Param("classroomId") int classroomId, @Param("date") String date) {
        return new SQL(){{
            SELECT("sc_users.id, sc_users.fullname, sc_users.gender, sc_attendances.*");
            FROM("sc_classrooms");
            INNER_JOIN("sc_classenrolls ON sc_classenrolls.classroom_id = sc_classrooms.id");
            INNER_JOIN("sc_users ON sc_classenrolls.user_id = sc_users.id");
            INNER_JOIN("sc_attendances ON sc_attendances.classenroll_id = sc_classenrolls.id");
            WHERE("sc_classenrolls.classroom_id = #{classroomId}");
            AND();
            WHERE("sc_attendances.date_from BETWEEN '${date}' || '-01' AND '${date}' || '-31'");
            AND();
            WHERE("sc_attendances.status = 'a'");
            ORDER_BY("sc_users.fullname ASC");
            ORDER_BY("sc_attendances.date_from ASC");
        }}.toString();
    }

    public String findAllStudentByClassroomId(@Param("classroomId") int classroomId) {
        return new SQL(){{
            SELECT("sc_users.ID, sc_users.fullname, sc_users.gender");
            FROM("sc_classenrolls");
            INNER_JOIN("sc_users ON sc_classenrolls.user_id = sc_users.ID");
            INNER_JOIN("sc_user_roles ON sc_user_roles.user_id = sc_users.ID");
            INNER_JOIN("sc_roles ON sc_user_roles.role_id = sc_roles.ID");
            WHERE("sc_classenrolls.classroom_id = #{classroomId} AND sc_roles.ROLE = 'STUDENT'");
            ORDER_BY("sc_users.fullname ASC");
        }}.toString();
    }

    public String findAllEnrollByClassroomId(@Param("classroomId") int classroomId) {
        return new SQL(){{
            SELECT("sc_classenrolls.id AS enroll_id, sc_users.id AS user_id, sc_users.fullname, sc_users.gender, sc_users.status, sc_users.deleted");
            FROM("sc_classenrolls");
            INNER_JOIN("sc_users ON sc_classenrolls.user_id = sc_users.id");
            INNER_JOIN("sc_user_roles ON sc_user_roles.user_id = sc_users.id");
            INNER_JOIN("sc_roles ON sc_user_roles.role_id = sc_roles.id");
            WHERE("sc_classenrolls.classroom_id = #{classroomId}");
            AND();
            WHERE("sc_roles.role = 'STUDENT'");
            AND();
            WHERE("sc_users.status = 't'");
            AND();
            WHERE("sc_users.deleted = 'f'");
            ORDER_BY("sc_users.fullname ASC");
        }}.toString();
    }
}
