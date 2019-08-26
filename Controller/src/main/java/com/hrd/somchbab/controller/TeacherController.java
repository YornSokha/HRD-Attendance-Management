package com.hrd.somchbab.controller;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.helper.UploadImage;
import com.hrd.somchbab.helper.Validator;
import com.hrd.somchbab.repository.model.Classenroll;
import com.hrd.somchbab.repository.model.TeacherAssignment;
import com.hrd.somchbab.repository.model.User;
import com.hrd.somchbab.service.academic_service.AcademicService;
import com.hrd.somchbab.service.currentuser.CurrentUserServiceImpl;
import com.hrd.somchbab.service.dashboard_service.DashboardService;
import com.hrd.somchbab.service.teacher_assignment_service.TeacherAssignmentService;
import com.hrd.somchbab.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Value("${file.server.path}")
    private String serverPath;

    @Autowired
    CurrentUserServiceImpl currentUserService;

    @Autowired
    private UserService userService;
    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private AcademicService academicService;

    @Autowired
    private TeacherAssignmentService teacherAssignmentService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private boolean image;


    @GetMapping("")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("teacher", getCurrentUser());
        return "teacher/home";
    }

    @PreAuthorize("@currentUserServiceImpl.adminAccess(principal)")
    @GetMapping("/list")
    public String list(@ModelAttribute User user, ModelMap modelMap, HttpServletRequest httpServletRequest
            , Paging paging, @RequestParam(defaultValue = "10") Integer limitStatic) {
        paging.setTotalCount(userService.findAllStudents().size());
        String params = httpServletRequest.getQueryString();
        String[] queries;
        List<String> selectFilters = null;
        if (params != null && !params.contains("page") & !params.contains("lang")) {
            queries = httpServletRequest.getQueryString().split(Pattern.quote("&"));
            selectFilters = getSelectFilters(queries);
        }
        String fullName = user.getFullName();
        // check if there is a query string to search for teacher's names
        if (fullName != null) {
            if (!fullName.isEmpty()) {
                setFiltersWithQueryString(modelMap, user, selectFilters, fullName, paging);
            } else {
                setFiltersWithoutQueryString(modelMap, selectFilters, paging);
            }
        } else {
            modelMap.addAttribute("user", new User());
            modelMap.addAttribute("result", "<span class='h3'>[All]</span>");
            modelMap.addAttribute("teachers", userService.findAllTeachersByPaging(paging));
        }
        if (Paging.limitStatic != 10)
            limitStatic = Paging.limitStatic;
        modelMap.addAttribute("limitStatic", limitStatic);
        modelMap.addAttribute("classenroll", new Classenroll());
        modelMap.addAttribute("academics", academicService.findAll());
        return "/teacher/list";
    }

    private List<String> getSelectFilters(String[] queries) {
        List<String> selectFilters = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            selectFilters.add(queries[i].substring(queries[i].lastIndexOf("=") + 1));
        }
        return selectFilters;
    }
    private List<String> getSelectFiltersForStudent(String[] queries) {
        List<String> selectFilters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            selectFilters.add(queries[i].substring(queries[i].lastIndexOf("=") + 1));
        }
        return selectFilters;
    }
    private void setFiltersWithoutQueryString(ModelMap modelMap, List<String> selectFilters, Paging paging) {
        modelMap.addAttribute("user", new User());

        // if there is page limit is changed
        if (Validator.isInteger(selectFilters.get(5))) {
            paging.setLimit(Integer.parseInt(selectFilters.get(5)));
        }

        // check if classroom is selected
        if (Validator.isInteger(selectFilters.get(3))) {
            modelMap.addAttribute("teachers", userService.filterTeachersByClassroomId(Integer.parseInt(selectFilters.get(3)), paging));
        } else if (Validator.isInteger(selectFilters.get(2))) {
            modelMap.addAttribute("teachers", userService.filterTeachersByCourseId(Integer.parseInt(selectFilters.get(2)), paging));
        } else if (Validator.isInteger(selectFilters.get(1))) {
            modelMap.addAttribute("teachers", userService.filterTeachersByGenerationId(Integer.parseInt(selectFilters.get(1)), paging));
        } else if (Validator.isInteger(selectFilters.get(0))) {
            modelMap.addAttribute("teachers", userService.filterTeachersByAcademicId(Integer.parseInt(selectFilters.get(0)), paging));
//            modelMap.addAttribute("teachers", userService.filterTeachersByAcademicId(Integer.parseInt(selectFilters.get(0))));
        } else {
            modelMap.addAttribute("user", new User());
            modelMap.addAttribute("result", "<span class='h3'>[All]</span>");
            modelMap.addAttribute("teachers", userService.findAllTeachersByPaging(paging));
        }
    }
    private void setFiltersWithQueryString(ModelMap modelMap, User user, List<String> selectFilters, String fullName, Paging paging) {
        modelMap.addAttribute("user", user);
        // check if classroom is selected

        if (Validator.isInteger(selectFilters.get(3))) {
            modelMap.addAttribute("teachers", userService.filterTeachersByNameAndClassroomId(fullName, Integer.parseInt(selectFilters.get(3)), paging));
        } else if (Validator.isInteger(selectFilters.get(2))) {
            modelMap.addAttribute("teachers", userService.filterTeachersByNameAndCourseId(fullName, Integer.parseInt(selectFilters.get(2)), paging));
        } else if (Validator.isInteger(selectFilters.get(1))) {
            modelMap.addAttribute("teachers", userService.filterTeachersByNameAndGenerationId(fullName, Integer.parseInt(selectFilters.get(1)), paging));
        } else if (Validator.isInteger(selectFilters.get(0))) {
            modelMap.addAttribute("teachers", userService.filterTeachersByNameAndAcademicId(fullName, Integer.parseInt(selectFilters.get(0)), paging));
        } else {
            modelMap.addAttribute("teachers", userService.filterTeachersByName(fullName, paging));
        }
    }

    @PreAuthorize("@currentUserServiceImpl.adminAccess(principal)")
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.addAttribute("teacher", new User());
        return "/teacher/add";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute User user, BindingResult bindingResult,
                         @RequestParam MultipartFile file, ModelMap modelMap) {
        user.setCreatedBy(getCurrentUserId());

        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("teacher", user);
            return "/teacher/add";
        }

        new UploadImage().upload(user, file, serverPath);
        user.setPassword("$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO."); // = 123
        userService.add(user);
        int userId = userService.getLastId();
        userService.addRoleToUser(userId, 3);
        return "redirect:/teacher/list";
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") int id, ModelMap modelMap) {
        modelMap.addAttribute("teacher", userService.findById(id));
        return "/teacher/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user, BindingResult bindingResult, @RequestParam MultipartFile file, ModelMap modelMap) {
        String currentPhoto = userService.findById(user.getId()).getPhoto();
        image = new UploadImage().upload(user, file, serverPath);
        if (!image) {
            user.setPhoto(currentPhoto);
        }
        if (user.getPassword() == null) {
            user.setPassword(userService.findById(user.getId()).getPassword());
        } else {
            if (!user.getPassword().isEmpty()) {
                user.setPassword(encryptPassword(user.getPassword()));
            } else {
                user.setPassword(userService.findById(user.getId()).getPassword());
            }
        }
        userService.update(user);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        String redirectUrl = "";
        if (hasUserRole) {
            redirectUrl = "redirect:/teacher/list";
        } else {
            redirectUrl = "redirect:/logout";
        }

        return redirectUrl;
    }

    @GetMapping("/{id}")
    public String view(@PathVariable int id, ModelMap modelMap) {
        modelMap.addAttribute("teacher", userService.findById(id));
        return "/teacher/view";
    }

    @GetMapping("/request-permission")
    public String requestPermission() {
        return "/teacher/request-permission";
    }

    @GetMapping("/view-pending-permission")
    public String viewPermission() {
        return "/teacher/view-pending-permission";
    }

    @GetMapping("/view-student-list")
    public String viewStudentList(@ModelAttribute User user, ModelMap modelMap, HttpServletRequest httpServletRequest
            , Paging paging, @RequestParam(defaultValue = "10") Integer limitStatic) {
        paging.setTotalCount(userService.findAllStudents().size());
        String params = httpServletRequest.getQueryString();
        String[] queries;
        List<String> selectFilters = null;
        if (params != null && !params.contains("page") & !params.contains("lang")) {
            queries = httpServletRequest.getQueryString().split(Pattern.quote("&"));
            selectFilters = getSelectFiltersForStudent(queries);
        }
        // check if no filter
        if(selectFilters==null){
            modelMap.addAttribute("user", new User());
            modelMap.addAttribute("students", userService.findAllStudentsByPaging(paging));
        }else{
            if (Validator.isInteger(selectFilters.get(4))) {
                paging.setLimit(Integer.parseInt(selectFilters.get(4)));
            }
            if (Validator.isInteger(selectFilters.get(3))) {
                modelMap.addAttribute("students", userService.filterStudentsByClassroomId(Integer.parseInt(selectFilters.get(3)), paging));
            } else if (Validator.isInteger(selectFilters.get(2))) {
                modelMap.addAttribute("students", userService.filterStudentsByCourseId(Integer.parseInt(selectFilters.get(2)), paging));
            } else if (Validator.isInteger(selectFilters.get(1))) {
                modelMap.addAttribute("students", userService.filterStudentsByGenerationId(Integer.parseInt(selectFilters.get(1)), paging));
            } else if (Validator.isInteger(selectFilters.get(0))) {
                modelMap.addAttribute("students", userService.filterStudentsByAcademicId(Integer.parseInt(selectFilters.get(0)), paging));
            } else {
                modelMap.addAttribute("user", new User());
                modelMap.addAttribute("students", userService.findAllStudentsByPaging(paging));
            }
        }
        if (Paging.limitStatic != 10)
            limitStatic = Paging.limitStatic;
        modelMap.addAttribute("limitStatic", limitStatic);
        modelMap.addAttribute("classenroll", new Classenroll());
        modelMap.addAttribute("academics", academicService.findAll());
        return "/teacher/view-student-list";
    }

    @DeleteMapping("/{id}")
    public String deleteTeacher(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/teacher/list";
    }

    @PostMapping("/assign")
    public String assign(@ModelAttribute TeacherAssignment teacherAssignment) {
        teacherAssignmentService.assignTeacher(teacherAssignment);
        return "redirect:/teacher/list";
    }

    private int getCurrentUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
    private User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    private String encryptPassword(String plainText) {
        return passwordEncoder.encode(plainText);
    }

}