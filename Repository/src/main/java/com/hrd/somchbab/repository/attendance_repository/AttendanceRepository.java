package com.hrd.somchbab.repository.attendance_repository;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.model.Attendance;
import com.hrd.somchbab.repository.provider.AttendanceProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository {

    /*@Select("SELECT\n" +
            "\tu.fullname,\n" +
            "\tA.*,\n" +
            "\t\ten.*\n" +
            "FROM\n" +
            "\tsc_attendances\n" +
            "\tA INNER JOIN sc_classenrolls en ON en.ID = A.classenroll_id\n" +
            "\tINNER JOIN sc_users u ON u.ID = en.user_id")
    @Results({
            @Result(property = "classenroll.id", column = "classenrolll_id"),
//            @Result(property = "user.fullname", column = "fullname")
    })*/
    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.deleted = FALSE ORDER BY att.id DESC")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> findAll();

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.deleted = FALSE AND att.status = 'p' ORDER BY att.id DESC")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> findPendingRequest();

    @InsertProvider(method = "request", type = AttendanceProvider.class)
    void request(Attendance attendance);

    @Select("SELECT att.*, en.id enroll_id FROM sc_attendances att INNER JOIN sc_classenrolls en ON att.classenroll_id = en.ID INNER JOIN sc_users u ON u.id =  en.user_id WHERE att.deleted = FALSE and u.id = #{id}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> findStudentAttendanceById(int id);

    @Select("SELECT att.* from sc_attendances att WHERE att.id = #{id} AND att.deleted = 'f' AND att.status = 'p'")
    @Results({
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
            @Result(property = "leaveTime", column = "leave_time"),
            @Result(property = "arriveTime", column = "arrive_time"),
            @Result(property = "amPm", column = "am_pm"),
            @Result(property = "createdAt", column = "created_at")
    })
    Attendance findById(int id);

    @Update("UPDATE sc_attendances SET admin_response_status = 'a', teacher_response_status = 'a', status = 'a' WHERE id = #{id}")
    void approve(int id);

    @Update("UPDATE sc_attendances SET admin_response_status = 'a' WHERE id = #{id}")
    void confirmByAdmin(int id);

    @Select("SELECT teacher_response_status FROM sc_attendances WHERE deleted=false and id = #{id}")
    Character getResponseStatusByTeacher(@Param("id") int id);

    @Update("UPDATE sc_attendances SET teacher_response_status = 'a' WHERE id = #{id}")
    void confirmByTeacher(@Param("id") int id);

    @Select("SELECT admin_response_status FROM sc_attendances WHERE deleted = 'f' and id = #{id}")
    Character getResponseStatusByAdmin(@Param("id") int id);

    @Update("UPDATE sc_attendances SET admin_response_status = 'r', status = 'r' WHERE id = #{id}")
    void rejectByAdmin(int id);

    @Update("UPDATE sc_attendances SET teacher_response_status = 'r', status = 'r' WHERE id = #{id}")
    void rejectByTeacher(int id);

    @Select("SELECT u.fullname, att.*, en.id enroll_id, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id INNER JOIN sc_classrooms c on c.id = en.classroom_id WHERE att.deleted = FALSE AND att.status = 'p' AND c.id in (SELECT c.id FROM sc_classrooms c INNER JOIN sc_teacher_assignments ta on c.id = ta.classroom_id \n" +
            "INNER JOIN sc_users u on u.id = ta.user_id WHERE u.id = #{id} and ta.class_teacher = true and u.deleted = FALSE and u.status = true) ORDER BY att.id DESC")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> findPendingRequestByTeacherId(@Param("id") int id);

    @Update("UPDATE sc_attendances SET deleted = TRUE WHERE id = #{id}")
    void delete(@Param("id") int id);

    @SelectProvider(method = "findTotalAttendanceByType", type = AttendanceProvider.class)
    int findTotalAttendanceByType(@Param("courseId") int courseId,
                                  @Param("userId") int userId,
                                  @Param("date") String date,
                                  @Param("leaveType") String leaveType);

    @SelectProvider(method = "findRequestingAttendanceByStudentId", type = AttendanceProvider.class)
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> findRequestingAttendanceByStudentId(@Param("id") int id);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.date_from >= #{dateFrom} AND att.date_to <= #{dateTo} AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> findAllByPagingAndDate(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> findAllByPaging(@Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE u.fullname ILIKE '%' || #{fullName} || '%' AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> findByName(@Param("fullName") String fullName, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.leave_status = #{leaveType} AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByType(@Param("leaveType")String leaveType, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.date_from >= #{dateFrom} AND att.date_to <= #{dateTo} AND att.leave_status = #{leaveType} AND u.fullname ILIKE '%' || #{fullName} || '%' AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByNameAndTypeAndDateRange(@Param("fullName") String fullName, @Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo, @Param("leaveType") String leaveType, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.date_from >= #{dateFrom} AND att.leave_status = #{leaveType} AND u.fullname ILIKE '%' || #{fullName} || '%' AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByNameAndTypeAndDateFrom(@Param("fullName") String fullName, @Param("dateFrom") String dateFrom, @Param("leaveType") String leaveType, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.date_to <= #{dateTo} AND att.leave_status = #{leaveType} AND u.fullname ILIKE '%' || #{fullName} || '%' AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByNameAndTypeAndDateTo(@Param("fullName") String fullName, @Param("dateTo") String dateTo, @Param("leaveType") String leaveType, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.date_from >= #{dateFrom} AND att.date_to <= #{dateTo} AND u.fullname ILIKE '%' || #{fullName} || '%' AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByNameAndDateRange(@Param("fullName") String fullName, @Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.date_from >= #{dateFrom} AND u.fullname ILIKE '%' || #{fullName} || '%' AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByNameAndDateFrom(@Param("fullName") String fullName, @Param("dateFrom") String dateFrom,@Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.date_to <= #{dateTo} AND u.fullname ILIKE '%' || #{fullName} || '%' AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByNameAndDateTo(@Param("fullName") String fullName, @Param("dateTo") String dateTo, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.date_from >= #{dateFrom} AND att.date_to <= #{dateTo} AND att.leave_status = #{leaveType} AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByTypeAndDateRange(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo, @Param("leaveType") String leaveType, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.date_from >= #{dateFrom} AND att.leave_status = #{leaveType} AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByTypeAndDateFrom(@Param("dateFrom") String dateFrom, @Param("leaveType") String leaveType, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.date_to <= #{dateTo} AND att.leave_status = #{leaveType} AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByTypeAndDateTo(@Param("dateTo") String dateTo, @Param("leaveType") String leaveType, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.date_from >= #{dateFrom} AND att.date_to <= #{dateTo} AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByDateRange(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.date_from >= #{dateFrom} AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByDateFrom(@Param("dateFrom") String dateFrom, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.date_to <= #{dateTo} AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByDateTo(@Param("dateTo") String dateTo, @Param("paging") Paging paging);

    @Select("SELECT att.*, en.id enroll_id, u.fullname, u.id user_id FROM sc_attendances att INNER JOIN sc_classenrolls en on en.id = att.classenroll_id INNER JOIN sc_users u on u.id = en.user_id WHERE att.leave_status = #{leaveType} AND u.fullname ILIKE '%' || #{fullName} || '%' AND att.deleted = FALSE ORDER BY att.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "classenroll.id", column = "enroll_id"),
            @Result(property = "classenroll.user.fullName", column = "fullname"),
            @Result(property = "classenroll.user.id", column = "user_id"),
            @Result(property = "leaveStatus", column = "leave_status"),
            @Result(property = "dateFrom", column = "date_from"),
            @Result(property = "dateTo", column = "date_to"),
            @Result(property = "teacherResponseStatus", column = "teacher_response_status"),
            @Result(property = "adminResponseStatus", column = "admin_response_status"),
    })
    List<Attendance> filterByNameAndType(@Param("fullName") String fullName, @Param("leaveType") String leaveType, @Param("paging") Paging paging);
}
