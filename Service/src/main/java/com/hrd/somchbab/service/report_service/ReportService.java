package com.hrd.somchbab.service.report_service;

import com.hrd.somchbab.repository.model.Attendance;
import com.hrd.somchbab.repository.model.Classenroll;
import com.hrd.somchbab.repository.model.User;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReportService {
    List<Attendance> findAll(@Param("classroomId") int classroomId, @Param("date") String date);

    List<User> findAllStudentByClassroomId(@Param("classroomId") int classroomId);

    List<Classenroll> findAllEnrollByClassroomId(@Param("classroomId") int classroomId);
}
