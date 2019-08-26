package com.hrd.somchbab.repository.provider;

import com.hrd.somchbab.repository.model.Attendance;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class AttendanceProvider {
    public String request(Attendance attendance){
        return new SQL(){{
            INSERT_INTO("sc_attendances");
            VALUES("classenroll_id", "#{classenroll.id}");
            VALUES("date_from", "#{dateFrom}");
            VALUES("date_to", "#{dateTo}");
            VALUES("leave_status", "#{leaveStatus}");
            VALUES("reason", "#{reason}");
            VALUES("leave_time", "#{leaveTime}");
            VALUES("arrive_time", "#{arriveTime}");
            VALUES("am_pm", "#{amPm}");
            VALUES("permission_count", "#{permissionCount}");
            VALUES("duration", "#{duration}");
        }}.toString();
    }

    public String findTotalAttendanceByType(@Param("courseId") int courseId,
                                            @Param("userId") int userId,
                                            @Param("date") String date,
                                            @Param("leaveType") String leaveType) {

        return new SQL(){{
            if(leaveType.equals("p"))
                SELECT("COALESCE(SUM(sc_attendances.permission_count),0)");
            else
                SELECT("COUNT(sc_attendances.id)");

            FROM("sc_classrooms");
            INNER_JOIN("sc_classenrolls ON sc_classenrolls.classroom_id = sc_classrooms.id");
            INNER_JOIN("sc_attendances ON sc_attendances.classenroll_id = sc_classenrolls.id");
            WHERE("sc_classenrolls.status = 't'");
            AND();
            WHERE("sc_classenrolls.deleted = 'f'");
            AND();
            WHERE("sc_attendances.status = 'a'");
            AND();
            WHERE("sc_attendances.deleted = 'f'");
            AND();
            WHERE("sc_classrooms.course_id = #{courseId}");
            AND();
            WHERE("sc_classenrolls.user_id = #{userId}");
            AND();
            WHERE("sc_attendances.created_at BETWEEN to_timestamp(('${date}' || '-01'), 'YYYY-MM-DD') AND to_timestamp(('${date}' || '-31'), 'YYYY-MM-DD')");
            AND();
            WHERE("sc_attendances.leave_status = '${leaveType}'");
        }}.toString();
    }

    public String findRequestingAttendanceByStudentId(@Param("id") int id) {
        return new SQL(){{
            SELECT("u.fullname, att.*, en.ID enroll_id, u.ID user_id");
            FROM("sc_attendances att");
            INNER_JOIN("sc_classenrolls en ON en.ID = att.classenroll_id");
            INNER_JOIN("sc_users u ON u.ID = en.user_id");
            INNER_JOIN("sc_classrooms C ON C.ID = en.classroom_id");
            WHERE("att.deleted = FALSE");
            AND();
//            WHERE("att.status = 'p'");
//            AND();
            WHERE("u.id = #{id}");
        }}.toString();
    }
}
