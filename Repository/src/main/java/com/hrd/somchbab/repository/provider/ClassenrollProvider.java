package com.hrd.somchbab.repository.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ClassenrollProvider {

    public String findIdByUserIdAndClassroomId(@Param("userId") int userId, @Param("classroomID") int classroomId){
        return new SQL(){{
            SELECT("en.id");
            FROM("sc_classenrolls en");
            INNER_JOIN("sc_users u on u.id = en.user_id");
            INNER_JOIN("sc_classrooms c on c.id = en.classroom_id");
            WHERE("c.id = " + classroomId + " AND u.id = " + userId);

        }}.toString();
    }
}
