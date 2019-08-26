package com.hrd.somchbab.controller;


import com.hrd.somchbab.dto.MailRequest;
import com.hrd.somchbab.helper.Manipulator;
import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.helper.UploadImage;
import com.hrd.somchbab.helper.Validator;
import com.hrd.somchbab.repository.CurrentUser;
import com.hrd.somchbab.repository.model.Attendance;
import com.hrd.somchbab.repository.model.Classenroll;
import com.hrd.somchbab.repository.model.Classroom;
import com.hrd.somchbab.repository.model.User;
import com.hrd.somchbab.service.academic_service.AcademicService;
import com.hrd.somchbab.service.attendance_service.AttendanceService;
import com.hrd.somchbab.service.classenroll_service.ClassenrollService;
import com.hrd.somchbab.service.classroom_service.ClassroomService;
import com.hrd.somchbab.service.course_service.CourseService;
import com.hrd.somchbab.service.generation_service.GenerationService;
import com.hrd.somchbab.service.service.EmailService;
import com.hrd.somchbab.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private EmailService service;

    @Autowired
    private UserService userService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private ClassenrollService classenrollService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private AcademicService academicService;

    @Value("${file.server.path}")
    private String serverPath;

    @Autowired
    private GenerationService generationService;
    private boolean isImageUploaded;
    private String image;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CourseService courseService;

    @PreAuthorize("@currentUserServiceImpl.studentAccess(principal)")
    @GetMapping("")
    public String home(ModelMap modelMap) {
        modelMap.addAttribute("student", getCurrentUser());
        return "/student/home";
    }

    @SuppressWarnings("Duplicates")
    @PreAuthorize("@currentUserServiceImpl.adminAccess(principal)")
    @GetMapping("/list")
    public String list(@ModelAttribute User user, ModelMap modelMap,
                       HttpServletRequest httpServletRequest, Paging paging, @RequestParam(defaultValue = "10") Integer limitStatic) {
        // To continue page total number
        if (Paging.limitStatic != 10)
            limitStatic = Paging.limitStatic;
        paging.setTotalCount(userService.findAllStudents().size());
        String params = httpServletRequest.getQueryString();
        String[] queries;
        List<String> selectFilters = null;
        if (params != null && !params.contains("page") && !params.contains("lang")) {
            queries = httpServletRequest.getQueryString().split(Pattern.quote("&"));
            selectFilters = getSelectFilters(queries);
        }
        String fullName = user.getFullName();
        // check if there is a query string to search for student's names
        if (fullName != null) {
            if (!fullName.isEmpty()) {
                setFiltersWithQueryString(modelMap, user, selectFilters, fullName, paging);
            } else {
                setFiltersWithoutQueryString(modelMap, selectFilters, paging);
            }
        } else {
            modelMap.addAttribute("user", new User());
            modelMap.addAttribute("result", "<span class='h3'>[All]</span>");
            modelMap.addAttribute("students", userService.findAllStudentsByPaging(paging));
        }
        modelMap.addAttribute("limitStatic", limitStatic);
        modelMap.addAttribute("classenroll", new Classenroll());
        modelMap.addAttribute("academics", academicService.findAll());
        return "/student/list";
    }

    private List<String> getSelectFilters(String[] queries) {
        List<String> selectFilters = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
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
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 4) + "]</span>");
            modelMap.addAttribute("students", userService.filterStudentsByClassroomId(Integer.parseInt(selectFilters.get(3)), paging));
        } else if (Validator.isInteger(selectFilters.get(2))) {
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 3) + "]</span>");
            modelMap.addAttribute("students", userService.filterStudentsByCourseId(Integer.parseInt(selectFilters.get(2)), paging));
        } else if (Validator.isInteger(selectFilters.get(1))) {
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 2) + "]</span>");
            modelMap.addAttribute("students", userService.filterStudentsByGenerationId(Integer.parseInt(selectFilters.get(1)), paging));
        } else if (Validator.isInteger(selectFilters.get(0))) {
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 1) + "]</span>");
            modelMap.addAttribute("students", userService.filterStudentsByAcademicId(Integer.parseInt(selectFilters.get(0)), paging));
        } else {
            modelMap.addAttribute("user", new User());
            modelMap.addAttribute("result", "<span class='h3'>[All]</span>");
            modelMap.addAttribute("students", userService.findAllStudentsByPaging(paging));

        }
    }

    // This function is use to generate filter message
    private void setFiltersWithQueryString(ModelMap modelMap, User user, List<String> selectFilters,
                                           String fullName, Paging paging) {
        modelMap.addAttribute("user", user);
        List<User> students;
        // check if classroom is selected
        if (Validator.isInteger(selectFilters.get(3))) {
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 4) + "]</span>");
            students = userService.filterStudentsByNameAndClassroomId(fullName, Integer.parseInt(selectFilters.get(3)), paging);
            paging.setTotalCount(students.size());
            modelMap.addAttribute("students", students);
        } else if (Validator.isInteger(selectFilters.get(2))) {
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 3) + "]</span>");
            students = userService.filterStudentsByNameAndCourseId(fullName, Integer.parseInt(selectFilters.get(2)), paging);
            paging.setTotalCount(students.size());
            modelMap.addAttribute("students", students);
        } else if (Validator.isInteger(selectFilters.get(1))) {
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 2) + "]</span>");
            modelMap.addAttribute("students", userService.filterStudentsByNameAndGenerationId(fullName, Integer.parseInt(selectFilters.get(1)), paging));
        } else if (Validator.isInteger(selectFilters.get(0))) {
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 1) + "]</span>");
            modelMap.addAttribute("students", userService.filterStudentsByNameAndAcademicId(fullName, Integer.parseInt(selectFilters.get(0)), paging));
        } else {
            modelMap.addAttribute("result",
                    "<span class='h3'>[ALL]</span>");
            modelMap.addAttribute("students", userService.findStudentsByName(fullName, paging));
        }
    }

    private String getFilterResults(List<String> selectFilters, int range) {

        int[] idsFilter = new int[range];
        for (int i = 0; i < range; i++) {
            idsFilter[i] = Integer.parseInt(selectFilters.get(i));
        }
        return getFilterResults(idsFilter);
    }

    // To generate filter message result
    private String getFilterResults(int... idsFilter) {

        if (idsFilter.length == 1) {
            return academicService.findById(idsFilter[0]).getName();
        } else if (idsFilter.length == 2) {
            return academicService.findById(idsFilter[0]).getName() +
                    ">" + generationService.findById(idsFilter[1]).getName();
        } else if (idsFilter.length == 3) {
            return academicService.findById(idsFilter[0]).getName() +
                    ">" + generationService.findById(idsFilter[1]).getName() +
                    ">" + courseService.find(idsFilter[2]).getName();
        } else if (idsFilter.length == 4) {
            return academicService.findById(idsFilter[0]).getName() +
                    ">" + generationService.findById(idsFilter[1]).getName() +
                    ">" + courseService.find(idsFilter[2]).getName() +
                    ">" + classroomService.findByID(idsFilter[3]).getClassName().getName();
        }else {
            return "ALL";
        }
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @GetMapping("/{id}")
    public String view(@PathVariable(name = "id") int id, ModelMap modelMap) {

        User user = userService.findById(id);
        if (user != null) {
            image = user.getPhoto();// string
            modelMap.addAttribute("student", user);
        }
        ArrayList<Integer> classroomIds = new ArrayList<>();
        List<List<Classroom>> classrooms = new ArrayList<>();
        if (user.getClassrooms() != null) {
            // get all classroom id
            user.getClassrooms().forEach(x -> classroomIds.add(x.getId()));
            // set classrooms for updating
            classroomIds.forEach(classroomId -> classrooms.add(classroomService.findAllByCourseId(classroomService.findCourseIdByClassroomId(classroomId))));
        }
        modelMap.addAttribute("classroomLists", classrooms);
        return "/student/view";

    }

    @PreAuthorize("@currentUserServiceImpl.adminAccess(principal)")
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.addAttribute("student", new User());
        return "/student/add";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute User user, BindingResult bindingResult,
                         @RequestParam MultipartFile file, ModelMap modelMap) {
        user.setCreatedBy(getCurrentUserId());
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("student", user);
            return "/student/add";
        }
        new UploadImage().upload(user, file, serverPath);
        user.setPassword("$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO."); // = 123
        userService.add(user);
        int userId = userService.getLastId();
        userService.addRoleToUser(userId, 4);
        return "redirect:/student/list";
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @GetMapping("/{id}/edit")
    public String edit(ModelMap modelMap, @PathVariable int id) {
        User user = userService.findById(id);

        if (user != null) {
            image = user.getPhoto();// string
            modelMap.addAttribute("student", user);
        }
        ArrayList<Integer> classroomIds = new ArrayList<>();
        List<List<Classroom>> classrooms = new ArrayList<>();
        if (user.getClassrooms() != null) {
            // get all classroom id
            user.getClassrooms().forEach(x -> classroomIds.add(x.getId()));
            // set classrooms for updating
            classroomIds.forEach(classroomId -> classrooms.add(classroomService.findAllByCourseId(classroomService.findCourseIdByClassroomId(classroomId))));
        }
        modelMap.addAttribute("classroomLists", classrooms);
        return "/student/edit";
    }

    @PostMapping("update")
    public String update(@ModelAttribute User user, BindingResult bindingResult, @RequestParam MultipartFile file, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("student", user);
            return "/student/add";
        }
        String currentPhoto = userService.findById(user.getId()).getPhoto();
        new UploadImage().upload(user, file, serverPath);
        isImageUploaded = new UploadImage().upload(user, file, serverPath);
        if (!isImageUploaded) {
            user.setPhoto(currentPhoto);
        }
        if (!user.getPassword().isEmpty()) {
            user.setPassword(encryptPassword(user.getPassword()));
        } else {
            user.setPassword(userService.findById(user.getId()).getPassword());
        }
        userService.update(user);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        String redirectUrl = "";
        if (hasUserRole)
            redirectUrl = "redirect:/student/list";
        else
            redirectUrl = "redirect:/logout";
        return redirectUrl;
    }

//    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @GetMapping("/view-personal-records")
    public String viewStudent(ModelMap modelMap) {
        int userId = getCurrentUserId();
        modelMap.addAttribute("userId", userId);
        modelMap.addAttribute("academics", academicService.findAllByStudentID(userId));
        modelMap.addAttribute("attendances", attendanceService.findRequestingAttendanceByStudentId(userId));
        return "/student/view-personal-records";
    }

    @PreAuthorize("@currentUserServiceImpl.studentAccess(principal)")
    @GetMapping("/request-permission")
    public String requestPermission(ModelMap modelMap) {
        modelMap.addAttribute("attendance", new Attendance());
        modelMap.addAttribute("classrooms", classroomService.findByUserId(getCurrentUserId()));
        return "/student/request-permission";
    }

    private String getPermissionType(String permissionStatus){
        if (permissionStatus.equals("l")){
            return " late ";
        }else if (permissionStatus.equals("le")){
            return " leave early ";
        }else if(permissionStatus.equals("g")){
            return " go outside ";
        }else if(permissionStatus.equals("p")){
            return " take leave ";
        }
        return " take leave ";
    }

    private void sendEmail(Attendance attendance) {
// Get all admins email addresses
        List<String> adminEmails = new ArrayList<>();
        userService.findAllByRoleName("ADMIN").forEach(x -> adminEmails.add(x.getEmail()));
        // Get class teacher email addresses
        int classroomId = attendance.getClassenroll().getClassroom().getId();
        List<String> teacherEmails = new ArrayList<>();
        int teacherId = 0;
        List<User> teacherLists = userService.findClassTeacherByClassroomId(classroomId);
        if (teacherLists != null)
            teacherId = teacherLists.get(0).getId();
        userService.findClassTeacherByClassroomId(classroomId).forEach(x -> teacherEmails.add(x.getEmail()));
        String[] adminEmailsStringArray = adminEmails.toArray(new String[adminEmails.size()]);
        String[] teacherEmailsStringArray = teacherEmails.toArray(new String[teacherEmails.size()]);
//        String[] both = Stream.concat(Arrays.stream(adminEmailsStringArray), Arrays.stream(teacherEmailsStringArray)).toArray(String[]::new);
        MailRequest request = new MailRequest();
        MailRequest requestTeacher = new MailRequest();
        request.setFrom("somchbab@gmail.com");
        request.setSubject(getPermissionType(attendance.getLeaveStatus()));
        request.setReason(attendance.getReason());
        request.setName(getCurrentUser().getFullName());
        request.setTo(adminEmailsStringArray);
        Map<String, Object> model = new HashMap<>();
        model.put("subject", getPermissionType(attendance.getLeaveStatus()));
        model.put("name", getCurrentUser().getFullName());
        model.put("requestedDate", attendance.getDateFrom());
        model.put("leaveTime", attendance.getLeaveTime());
        model.put("arriveTime", attendance.getArriveTime());
        model.put("reason", attendance.getReason());
        // Todo rethink about dynamic link
        model.put("url", "http://110.74.194.125:15001/attendance/pending-request/");
        service.sendEmail(request, model);

        // send to teacher
        requestTeacher.setFrom("somchbab@gmail.com");
        requestTeacher.setSubject(getPermissionType(attendance.getLeaveStatus()));
        requestTeacher.setReason(attendance.getReason());
        requestTeacher.setName(getCurrentUser().getFullName());
        requestTeacher.setTo(teacherEmailsStringArray);
        model.put("url", "http://110.74.194.125:15001/attendance/pending-request/" + teacherId);
        service.sendEmail(requestTeacher, model);
    }

    @PostMapping("/request-permission")
    public String requestPermission(@Valid @ModelAttribute Attendance attendance,
                                    BindingResult bindingResult, ModelMap modelMap) {
        attendance.getClassenroll().setId(classenrollService.findIdByUserIdAndClassroomId(getCurrentUserId(), attendance.getClassenroll().getClassroom().getId()));
        String dateTo = attendance.getDateTo();
        if(dateTo.contains(",")){
            attendance.setDateTo(Manipulator.removeLastCharacter(dateTo));
        }
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("classrooms", classroomService.findByUserId(getCurrentUserId()));
            return "/student/request-permission";
        }
        attendanceService.request(attendance);
        sendEmail(attendance);
        return "redirect:/student";
    }

    private int getCurrentUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    private User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/student/list";
    }

    @PostMapping("/assign")
    public String assign(@ModelAttribute Classenroll classenroll) {
        classenrollService.enrollStudent(classenroll);
        return "redirect:/student/list";
    }

    private String encryptPassword(String plainText) {
        return passwordEncoder.encode(plainText);
    }

    private ArrayList<String> getUserRoles(CurrentUser currentUser) {
        return (ArrayList<String>) currentUser.getRoles().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    @DeleteMapping("/attendance/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        attendanceService.delete(id);
        return "redirect:/student/view-personal-records";
    }
}
