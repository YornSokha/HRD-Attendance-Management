package com.hrd.somchbab.rest_controller;

import com.hrd.somchbab.service.attendance_service.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/student")
public class StudentRestController {

    @Autowired
    AttendanceService attendanceService;

    @GetMapping(value = "{courseId}/courseId/{userId}/userId/{date}/date/{leaveType}/leaveType")
    public Map<String, Object> getTotalAttendance(@PathVariable("courseId") int courseId,
                                                  @PathVariable("userId") int userId,
                                                  @PathVariable("date") String date,
                                                  @PathVariable("leaveType") String leaveType) {
        Map<String, Object> reportMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();


        dataMap.put("totalGoOutside", attendanceService.findTotalAttendanceByType(courseId, userId, date,"g"));
        dataMap.put("totalLate", attendanceService.findTotalAttendanceByType(courseId, userId, date, "l"));
        dataMap.put("totalPermission", attendanceService.findTotalAttendanceByType(courseId, userId, date, "p"));
        dataMap.put("totalAbsent", attendanceService.findTotalAttendanceByType(courseId, userId, date, "a"));

        reportMap.put("data", dataMap);
        reportMap.put("status", reportMap != null);
        return reportMap;
    }
}
