package com.hrd.somchbab.service;

import com.hrd.somchbab.repository.model.Attendance;
import com.hrd.somchbab.repository.model.Classenroll;
import com.hrd.somchbab.repository.model.User;
import com.hrd.somchbab.repository.report_repository.ReportRepository;
import com.hrd.somchbab.service.report_service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImp implements ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Override
    public List<Attendance> findAll(int classroomId, String date) {
        return reportRepository.findAll(classroomId, date);
    }

    @Override
    public List<User> findAllStudentByClassroomId(int classroomId) {
        return reportRepository.findAllStudentByClassroomId(classroomId);
    }

    @Override
    public List<Classenroll> findAllEnrollByClassroomId(int classroomId) {
        return reportRepository.findAllEnrollByClassroomId(classroomId);
    }
}
