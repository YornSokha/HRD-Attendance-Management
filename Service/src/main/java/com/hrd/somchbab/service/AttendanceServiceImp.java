package com.hrd.somchbab.service;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.attendance_repository.AttendanceRepository;
import com.hrd.somchbab.repository.model.Attendance;
import com.hrd.somchbab.service.attendance_service.AttendanceService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServiceImp implements AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> findAll() {
        return attendanceRepository.findAll();
    }

    @Override
    public void request(Attendance attendance) {
        attendanceRepository.request(attendance);
    }

    @Override
    public List<Attendance> findPendingRequest() {
        return attendanceRepository.findPendingRequest();
    }

    @Override
    public List<Attendance> findStudentAttendanceById(int id) {
        return attendanceRepository.findStudentAttendanceById(id);
    }

    @Override
    public Attendance findById(int id) {
        return attendanceRepository.findById(id);
    }

    @Override
    public void approve(int id) {
        attendanceRepository.approve(id);
    }

    @Override
    public void confirmByAdmin(int id) {
        attendanceRepository.confirmByAdmin(id);
    }

    @Override
    public Character getResponseStatusByTeacher(int id) {
        return attendanceRepository.getResponseStatusByTeacher(id);
    }

    @Override
    public void confirmByTeacher(int id) {
        attendanceRepository.confirmByTeacher(id);
    }

    @Override
    public Character getResponseStatusByAdmin(int id) {
        return attendanceRepository.getResponseStatusByAdmin(id);
    }

    @Override
    public void rejectByAdmin(int id) {
        attendanceRepository.rejectByAdmin(id);
    }

    @Override
    public void rejectByTeacher(int id) {
        attendanceRepository.rejectByTeacher(id);
    }

    @Override
    public List<Attendance> findPendingRequestByTeacherId(int id) {
        return attendanceRepository.findPendingRequestByTeacherId(id);
    }

    @Override
    public void delete(int id) {
        attendanceRepository.delete(id);
    }

    @Override
    public int findTotalAttendanceByType(@Param("courseId") int courseId,
                                         @Param("userId") int userId,
                                         @Param("date") String date,
                                         @Param("leaveType") String leaveType) {
        return attendanceRepository.findTotalAttendanceByType(courseId, userId, date, leaveType);
    }

    @Override
    public List<Attendance> findRequestingAttendanceByStudentId(@Param("id") int id) {
        return attendanceRepository.findRequestingAttendanceByStudentId(id);
    }

    public List<Attendance> findAllByPagingAndDate(String dateFrom, String dateTo,Paging paging) {
        return attendanceRepository.findAllByPagingAndDate(dateFrom, dateTo,paging);
    }

    @Override
    public List<Attendance> findAllByPaging(Paging paging) {
        return attendanceRepository.findAllByPaging(paging);
    }

    @Override
    public List<Attendance> filterByType(String leaveType, Paging paging) {
        return attendanceRepository.filterByType(leaveType, paging);
    }

    @Override
    public List<Attendance> filterByNameAndTypeAndDateRange(String fullName,String dateFrom, String dateTo, String leaveType, Paging paging) {
        return attendanceRepository.filterByNameAndTypeAndDateRange(fullName, dateFrom, dateFrom,leaveType, paging);
    }

    @Override
    public List<Attendance> findByName(String fullName, Paging paging) {
        return attendanceRepository.findByName(fullName, paging);
    }

    @Override
    public List<Attendance> filterByNameAndTypeAndDateFrom(String fullName, String dateFrom, String leaveType, Paging paging) {
        return attendanceRepository.filterByNameAndTypeAndDateFrom(fullName, dateFrom, leaveType, paging);
    }

    @Override
    public List<Attendance> filterByNameAndTypeAndDateTo(String fullName, String dateTo, String leaveType, Paging paging) {
        return attendanceRepository.filterByNameAndTypeAndDateTo(fullName, dateTo, leaveType, paging);
    }

    @Override
    public List<Attendance> filterByNameAndDateRange(String fullName, String dateFrom, String dateTo, Paging paging) {
        return attendanceRepository.filterByNameAndDateRange(fullName, dateFrom, dateTo, paging);
    }

    @Override
    public List<Attendance> filterByNameAndDateFrom(String fullName, String dateFrom, Paging paging) {
        return attendanceRepository.filterByNameAndDateFrom(fullName, dateFrom, paging);
    }

    @Override
    public List<Attendance> filterByNameAndDateTo(String fullName, String dateTo, Paging paging) {
        return attendanceRepository.filterByNameAndDateTo(fullName, dateTo, paging);
    }

    @Override
    public List<Attendance> filterByTypeAndDateRange(String dateFrom, String dateTo, String leaveType, Paging paging) {
        return attendanceRepository.filterByTypeAndDateRange(dateFrom, dateTo, leaveType, paging);
    }

    @Override
    public List<Attendance> filterByTypeAndDateFrom(String dateFrom, String leaveType, Paging paging) {
        return attendanceRepository.filterByTypeAndDateFrom(dateFrom, leaveType, paging);
    }

    @Override
    public List<Attendance> filterByTypeAndDateTo(String dateTo, String leaveType, Paging paging) {
        return attendanceRepository.filterByTypeAndDateTo(dateTo, leaveType, paging);
    }

    @Override
    public List<Attendance> filterByDateRange(String dateFrom, String dateTo, Paging paging) {
        return attendanceRepository.filterByDateRange(dateFrom, dateTo, paging);
    }

    @Override
    public List<Attendance> filterByDateFrom(String dateFrom, Paging paging) {
        return attendanceRepository.filterByDateFrom(dateFrom, paging);
    }

    @Override
    public List<Attendance> filterByDateTo(String dateTo, Paging paging) {
        return attendanceRepository.filterByDateTo(dateTo, paging);
    }

    @Override
    public List<Attendance> filterByNameAndType(String fullName, String leaveType, Paging paging) {
        return attendanceRepository.filterByNameAndType(fullName, leaveType, paging);
    }
}
