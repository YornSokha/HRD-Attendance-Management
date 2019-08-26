package com.hrd.somchbab.service.attendance_service;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.model.Attendance;

import java.util.List;

public interface AttendanceService {

    List<Attendance> findAll();
    void request(Attendance attendance);
    List<Attendance> findPendingRequest();

    List<Attendance> findStudentAttendanceById(int id);

    Attendance findById(int id);

    void approve(int id);
    void confirmByAdmin(int id);

    Character getResponseStatusByTeacher(int id);
    void confirmByTeacher(int id);
    Character getResponseStatusByAdmin(int id);

    void rejectByAdmin(int id);

    void rejectByTeacher(int id);

    List<Attendance> findPendingRequestByTeacherId(int id);

    void delete(int id);


    int findTotalAttendanceByType(int courseId, int userId, String date, String leaveType);

    List<Attendance> findRequestingAttendanceByStudentId(int id);

    List<Attendance> findAllByPagingAndDate(String dateFrom, String dateTo,Paging paging);
    List<Attendance> findAllByPaging(Paging paging);

    List<Attendance> filterByType(String leaveType, Paging paging);

    List<Attendance> filterByNameAndTypeAndDateRange(String fullName, String dateFrom, String dateTo, String leaveType, Paging paging);

    List<Attendance> findByName(String fullName, Paging paging);

    List<Attendance> filterByNameAndTypeAndDateFrom(String fullName, String dateFrom, String leaveType, Paging paging);

    List<Attendance> filterByNameAndTypeAndDateTo(String fullName, String dateTo, String leaveType, Paging paging);

    List<Attendance> filterByNameAndDateRange(String fullName, String dateFrom, String dateTo, Paging paging);

    List<Attendance> filterByNameAndDateFrom(String fullName, String dateFrom, Paging paging);

    List<Attendance> filterByNameAndDateTo(String fullName, String dateTo, Paging paging);

    List<Attendance> filterByTypeAndDateRange(String dateFrom, String dateTo, String leaveType, Paging paging);

    List<Attendance> filterByTypeAndDateFrom(String dateFrom, String leaveType, Paging paging);

    List<Attendance> filterByTypeAndDateTo(String dateTo, String leaveType, Paging paging);

    List<Attendance> filterByDateRange(String dateFrom, String dateTo, Paging paging);

    List<Attendance> filterByDateFrom(String dateFrom, Paging paging);

    List<Attendance> filterByDateTo(String dateTo, Paging paging);

    List<Attendance> filterByNameAndType(String fullName, String leaveType, Paging paging);
}
