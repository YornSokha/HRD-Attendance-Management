package com.hrd.somchbab.repository.report_repository;

import com.hrd.somchbab.repository.model.Attendance;
import com.hrd.somchbab.repository.model.Classenroll;
import com.hrd.somchbab.repository.model.User;
import com.hrd.somchbab.repository.provider.ReportProvider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository {
    @SelectProvider(method = "findAll", type = ReportProvider.class)
    @Results({
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.gender", column = "gender"),
            @Result(property = "classenroll.id", column = "classenroll_id"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
            @Result(property = "leaveTime", column = "leave_time"),
            @Result(property = "arriveTime", column = "arrive_time"),
            @Result(property = "amPm", column = "am_pm"),
            @Result(property = "permissionCount", column = "permission_count"),
            @Result(property = "createdAt", column = "created_at")
    })
    List<Attendance> findAll(@Param("classroomId") int classroomId, @Param("date") String date);

    @SelectProvider(method = "findAllStudentByClassroomId", type = ReportProvider.class)
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "status", column = "status"),
            @Result(property = "deleted", column = "deleted"),
            @Result(property = "gender", column = "gender"),
    })
    List<User> findAllStudentByClassroomId(@Param("classroomId") int classroomId);

    @SelectProvider(method = "findAllEnrollByClassroomId", type = ReportProvider.class)
    @Results({
            @Result(property = "id", column = "enroll_id"),
            @Result(property = "user.id", column = "user_id"),
            @Result(property = "user.fullName", column = "fullname"),
            @Result(property = "user.gender", column = "gender"),
            @Result(property = "user.status", column = "status"),
            @Result(property = "user.deleted", column = "deleted")
    })
    List<Classenroll> findAllEnrollByClassroomId(@Param("classroomId") int classroomId);
}
