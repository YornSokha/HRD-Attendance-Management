package com.hrd.somchbab.rest_controller;

import com.hrd.somchbab.repository.model.Attendance;
import com.hrd.somchbab.service.attendance_service.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/attendance")
public class AttendanceRestController {

    @Autowired
    AttendanceService attendanceService;

    @GetMapping(value = "{id}")
    public Map<String, Object> getAttendanceById(@PathVariable("id") int id) {
        Map<String, Object> attendanceMap = new HashMap<>();
        Attendance attendance = attendanceService.findById(id);
        attendanceMap.put("data", attendance);
        attendanceMap.put("status", attendance != null);
        return attendanceMap;
    }
}

