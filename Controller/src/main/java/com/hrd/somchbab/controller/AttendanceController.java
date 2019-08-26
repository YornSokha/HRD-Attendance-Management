package com.hrd.somchbab.controller;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.helper.Validator;
import com.hrd.somchbab.repository.model.Attendance;
import com.hrd.somchbab.repository.model.User;
import com.hrd.somchbab.service.attendance_service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PreAuthorize("@currentUserServiceImpl.adminAccess(principal)")
    @GetMapping("")
    public String index(@ModelAttribute User user, ModelMap modelMap, HttpServletRequest httpServletRequest, Paging paging, @RequestParam(defaultValue = "10") Integer limitStatic) {
        paging.setTotalCount(attendanceService.findAll().size());
        String params = httpServletRequest.getQueryString();
        String[] queries;
        String fullName = null;
        List<String> selectFilters = null;
        if (params != null && !params.contains("page") && !params.contains("lang")) {
            queries = httpServletRequest.getQueryString().split(Pattern.quote("&"));
            selectFilters = getSelectFilters(queries);
            fullName = selectFilters.get(3);
        }
        // check if there is a query string to search for student's names
        if (fullName != null) {
            if (!fullName.isEmpty()) {
                setFiltersWithQueryString(modelMap, user, selectFilters, fullName, paging);
            } else {
                setFiltersWithoutQueryString(modelMap, selectFilters, paging);
            }
        } else {
            modelMap.addAttribute("user", new User());
            modelMap.addAttribute("attendances", attendanceService.findAllByPaging(paging));
        }
// To continue page total number
        if (Paging.limitStatic != 10)
            limitStatic = Paging.limitStatic;
        modelMap.addAttribute("limitStatic", limitStatic);
        return "attendance/index";
    }

    private List<String> getSelectFilters(String[] queries) {
        List<String> selectFilters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            selectFilters.add(queries[i].substring(queries[i].lastIndexOf("=") + 1));
        }
        return selectFilters;
    }

    private void setFiltersWithoutQueryString(ModelMap modelMap, List<String> selectFilters, Paging paging) {
        modelMap.addAttribute("user", new User());
        List<Attendance> attendances;
//         if there is page limit is changed
        if (Validator.isInteger(selectFilters.get(4))) {
            paging.setLimit(Integer.parseInt(selectFilters.get(4)));
        }
        // check if classroom is selected
        if (Validator.isInteger(selectFilters.get(0))) {

            if (!selectFilters.get(1).isEmpty() && !selectFilters.get(2).isEmpty()) {
                attendances = attendanceService.filterByTypeAndDateRange(selectFilters.get(1), selectFilters.get(2), convertPermissionType(Integer.parseInt(selectFilters.get(0))), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            } else if (!selectFilters.get(1).isEmpty()) {
                attendances = attendanceService.filterByTypeAndDateFrom(selectFilters.get(1), convertPermissionType(Integer.parseInt(selectFilters.get(0))), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            } else if (!selectFilters.get(2).isEmpty()) {
                attendances = attendanceService.filterByTypeAndDateTo(selectFilters.get(2), convertPermissionType(Integer.parseInt(selectFilters.get(0))), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            } else {
                attendances = attendanceService.filterByType(convertPermissionType(Integer.parseInt(selectFilters.get(0))), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            }
        } else {

            if (!selectFilters.get(1).isEmpty() && !selectFilters.get(2).isEmpty()) {
                attendances = attendanceService.filterByDateRange(selectFilters.get(1), selectFilters.get(2), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            } else if (!selectFilters.get(1).isEmpty()) {
                attendances = attendanceService.filterByDateFrom(selectFilters.get(1), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            } else if (!selectFilters.get(2).isEmpty()) {
                attendances = attendanceService.filterByDateTo(selectFilters.get(2), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            } else {
                modelMap.addAttribute("user", new User());
                modelMap.addAttribute("attendances", attendanceService.findAllByPaging(paging));
            }
        }
    }

    // This function is use to generate filter message
    private void setFiltersWithQueryString(ModelMap modelMap, User user, List<String> selectFilters,
                                           String fullName, Paging paging) {
        modelMap.addAttribute("user", user);
        List<Attendance> attendances;
        if (Validator.isInteger(selectFilters.get(0))) {
            if (!selectFilters.get(1).isEmpty() && !selectFilters.get(2).isEmpty()) {
                attendances = attendanceService.filterByNameAndTypeAndDateRange(fullName, selectFilters.get(1), selectFilters.get(2), convertPermissionType(Integer.parseInt(selectFilters.get(0))), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            } else if (!selectFilters.get(1).isEmpty()) {
                attendances = attendanceService.filterByNameAndTypeAndDateFrom(fullName, selectFilters.get(1), convertPermissionType(Integer.parseInt(selectFilters.get(0))), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            } else if (!selectFilters.get(2).isEmpty()) {
                attendances = attendanceService.filterByNameAndTypeAndDateTo(fullName, selectFilters.get(2), convertPermissionType(Integer.parseInt(selectFilters.get(0))), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            } else {
                attendances = attendanceService.filterByNameAndType(fullName, convertPermissionType(Integer.parseInt(selectFilters.get(0))), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            }
        } else {
            if (!selectFilters.get(1).isEmpty() && !selectFilters.get(2).isEmpty()) {
                attendances = attendanceService.filterByNameAndDateRange(fullName, selectFilters.get(1), selectFilters.get(2), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            } else if (!selectFilters.get(1).isEmpty()) {
                attendances = attendanceService.filterByNameAndDateFrom(fullName, selectFilters.get(1), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            } else if (!selectFilters.get(2).isEmpty()) {
                attendances = attendanceService.filterByNameAndDateTo(fullName, selectFilters.get(2), paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            } else {
                attendances = attendanceService.findByName(fullName, paging);
                paging.setTotalCount(attendances.size());
                modelMap.addAttribute("attendances", attendances);
            }
        }
    }

    private String convertPermissionType(int code) {
        if (code == 1) {
            return "l";
        } else if (code == 2) {
            return "le";
        } else if (code == 3) {
            return "g";
        } else if (code == 4) {
            return "p";
        } else {
            return null;
        }
    }

    @PreAuthorize("@currentUserServiceImpl.adminAccess(principal)")
    @GetMapping("/pending-request")
    public String pending(ModelMap modelMap) {
        modelMap.addAttribute("attendances", attendanceService.findPendingRequest());
        return "attendance/pending-admin";
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @GetMapping("/pending-request/{id}")
    public String pendingFilteredByTeacherId(ModelMap modelMap, @PathVariable(name = "id") int id) {
        modelMap.addAttribute("attendances", attendanceService.findPendingRequestByTeacherId(id));
        return "attendance/pending-teacher";
    }

    @PostMapping("/admin-approve/{id}")
    public String adminApprove(@PathVariable(name = "id") int id) {
        Character teacherResponseStatus = attendanceService.getResponseStatusByTeacher(id);
        if (teacherResponseStatus.equals('a')) {
            attendanceService.approve(id);
        } else {
            attendanceService.confirmByAdmin(id);
        }
            return "redirect:/attendance/pending-request";
    }

    @PostMapping("/admin-approve-index/{id}")
    public String adminApproveIndex(@PathVariable(name = "id") int id) {
        Character teacherResponseStatus = attendanceService.getResponseStatusByTeacher(id);
        if (teacherResponseStatus.equals('a')) {
            attendanceService.approve(id);
        } else {
            attendanceService.confirmByAdmin(id);
        }
        return "redirect:/attendance";
    }

    @PostMapping("/admin-reject/{id}")
    public String adminReject(@PathVariable(name = "id") int id) {
        attendanceService.rejectByAdmin(id);
        return "redirect:/attendance/pending-request";
    }

    @PostMapping("/admin-reject-index/{id}")
    public String adminRejectIndex(@PathVariable(name = "id") int id) {
        attendanceService.rejectByAdmin(id);
        return "redirect:/attendance";
    }

    @PostMapping("/teacher-approve/{id}")
    public String teacherConfirm(@PathVariable(name = "id") int id) {
        Character adminResponseStatus = attendanceService.getResponseStatusByAdmin(id);
        if (adminResponseStatus.equals('a')) {
            attendanceService.approve(id);
        } else {
            attendanceService.confirmByTeacher(id);
        }
        return "redirect:/attendance/pending-request/" + getCurrentUserId();
    }

    @PostMapping("/teacher-reject/{id}")
    public String teacherReject(@PathVariable(name = "id") int id) {
        attendanceService.rejectByTeacher(id);
        return "redirect:/attendance/pending-request/" + getCurrentUserId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        attendanceService.delete(id);
        return "redirect:/attendance";
    }

    private int getCurrentUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
