package com.hrd.somchbab.rest_controller;

import com.hrd.somchbab.helper.ReportCreation;
import com.hrd.somchbab.repository.model.Attendance;
import com.hrd.somchbab.repository.model.Classenroll;
import com.hrd.somchbab.repository.model.FileResponse;
import com.hrd.somchbab.repository.model.User;
import com.hrd.somchbab.service.reportstorage_service.ReportStorageService;
import com.hrd.somchbab.service.classroom_service.ClassroomService;
import com.hrd.somchbab.service.course_service.CourseService;
import com.hrd.somchbab.service.generation_service.GenerationService;
import com.hrd.somchbab.service.report_service.ReportService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/report")
public class ReportRestController {

    @Autowired
    ReportService reportService;

    @Autowired
    ClassroomService classroomService;

    @Autowired
    GenerationService generationService;

    @Autowired
    CourseService courseService;

    @Autowired
    ReportCreation reportCreation;

    @Autowired
    ReportStorageService reportStorageService;

    private static final Logger logger = LoggerFactory.getLogger(ReportRestController.class);

    @GetMapping(value = "{classroomId}/classroom/{date}/date")
    public Map<String, Object> getReportList(@PathVariable("classroomId") int classroomId,
                                             @PathVariable("date") String date) {
        Map<String, Object> reportMap = new HashMap<>();
        List<Attendance> attendanceList = reportService.findAll(classroomId, date);
        reportMap.put("data", attendanceList);
        reportMap.put("status", attendanceList != null);
        return reportMap;
    }

    @GetMapping(value = "{classroomId}/classroom")
    public Map<String, Object> getStudentList(@PathVariable("classroomId") int classroomId) {
        Map<String, Object> studentMap = new HashMap<>();
        List<User> studentList = reportService.findAllStudentByClassroomId(classroomId);
        studentMap.put("data", studentList);
        studentMap.put("status", studentList != null);
        return studentMap;
    }

    @GetMapping(value = "{classroomId}/classenroll")
    public Map<String, Object> getEnrollList(@PathVariable("classroomId") int classroomId) {
        Map<String, Object> enrollMap = new HashMap<>();
        List<Classenroll> enrollList = reportService.findAllEnrollByClassroomId(classroomId);
        enrollMap.put("data", enrollList);
        enrollMap.put("status", enrollList != null);
        return enrollMap;
    }

    @GetMapping(value = "/excel/{classroomId}/classroom/{date}/date")
    public Map<String, Object> generateExcelFormat(@PathVariable("classroomId") int classroomId,
                                                   @PathVariable("date") String date) {

        Map<String, Object> linkMap = new HashMap<>();
        FileResponse fileResponse = new FileResponse();
        String fileName = reportCreation.generateReport(classroomId, date);
        Resource resource = reportStorageService.loadFileAsResource(fileName);

        try {
            fileResponse.setFileName(fileName);
            fileResponse.setFileDownloadUri("/api/report/download/" + fileName);
            fileResponse.setFileType(Files.probeContentType(resource.getFile().toPath()));
            fileResponse.setSize(resource.getFile().length());
        }
        catch (IOException e) {
            logger.info("File Response Error!");
        }

        linkMap.put("data", fileResponse);
        linkMap.put("status", fileResponse != null);
        return linkMap;
    }

    @GetMapping(value = "/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = reportStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            // todo
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}