package com.hrd.somchbab.controller;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.helper.UploadImage;
import com.hrd.somchbab.helper.Validator;
import com.hrd.somchbab.repository.model.User;
import com.hrd.somchbab.service.academic_service.AcademicService;
import com.hrd.somchbab.service.dashboard_service.DashboardService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/director")
public class DirectorController {
    @Autowired
    private UserService userService;

    @Autowired
    private DashboardService dashboardService;

    @Value("${file.server.path}")
    private String serverPath;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AcademicService academicService;

    private boolean image;

    @GetMapping("")
    public String home(ModelMap modelMap){
        modelMap.addAttribute("director", getCurrentUser());
        modelMap.addAttribute("requestingPermission", dashboardService.findRequestingPermission());
        modelMap.addAttribute("requestedPermission", dashboardService.findRequestedPermission());
        modelMap.addAttribute("mostLate", dashboardService.findMostLate());
        return "/director/home";
    }

    @GetMapping("/list")
    public String index(@ModelAttribute User user, ModelMap modelMap, HttpServletRequest httpServletRequest, Paging paging, @RequestParam(defaultValue = "10") Integer limitStatic) {
        paging.setTotalCount(userService.findAllDirectors().size());
        String params = httpServletRequest.getQueryString();
        String[] queries;
        List<String> selectFilters = null;
        if (params != null && !params.contains("page") && !params.contains("lang")) {
            queries = httpServletRequest.getQueryString().split(Pattern.quote("&"));
            selectFilters = getSelectFilters(queries);
        }
        String directorName = user.getFullName();
        // check if there is a query string to search for generationName's names

        if (directorName != null) {
            if (!directorName.isEmpty()) {
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("directors",userService.findDirectorsByNameAndPaging(directorName,paging));
            } else {
                if (Validator.isInteger(selectFilters.get(1))) {
                    paging.setLimit(Integer.parseInt(selectFilters.get(1)));;;
                }
                modelMap.addAttribute("user", new User());
                modelMap.addAttribute("directors", userService.findAllDirectorByPaging(paging));
            }
        } else {
            modelMap.addAttribute("user", new User());
            modelMap.addAttribute("directors", userService.findAllDirectorByPaging(paging));
        }
        // To continue page total number
        if (Paging.limitStatic != 10)
            limitStatic = Paging.limitStatic;
        modelMap.addAttribute("limitStatic", limitStatic);
        return "/director/list";
    }
    private List<String> getSelectFilters(String[] queries) {
        List<String> selectFilters = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            selectFilters.add(queries[i].substring(queries[i].lastIndexOf("=") + 1));
        }
        return selectFilters;
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @GetMapping("/{id}")
    public String view(@PathVariable int id, ModelMap modelMap){
        modelMap.addAttribute("director",userService.findById(id));
        return "/director/view";
    }

    @GetMapping("/add")
    public String add(ModelMap modelMap){
        modelMap.addAttribute("director",new User());
        return "/director/add";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute User user, BindingResult bindingResult, MultipartFile file, ModelMap modelMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        user.setCreatedBy(((User) authentication.getPrincipal()).getId());
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("director", user);
            return "/director/add";
        }
        new UploadImage().upload(user, file, serverPath);
        user.setPassword("$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO."); // = 123
        userService.add(user);
        int userId = userService.getLastId();
        userService.addRoleToUser(userId, 1);
        return "redirect:/director/list";
    }

    boolean getUserRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        return hasUserRole;
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, ModelMap modelMap){
        modelMap.addAttribute("director", userService.findById(id));

        String redirectUrl = "";
        if(getUserRole())
        {
            redirectUrl = "/director/edit";
        }
        else{
            redirectUrl = "/director/profile";
        }
        return redirectUrl;
    }

    @PostMapping("update")
    public String update(@ModelAttribute User user,BindingResult bindingResult,@RequestParam MultipartFile file, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("director", user);
            return "/director/add";
        }
        String currentPhoto = userService.findById(user.getId()).getPhoto();
        new UploadImage().upload(user, file, serverPath);
        image = new UploadImage().upload(user, file, serverPath);
        if (!image) {
            user.setPhoto(currentPhoto);
        }
        if (!user.getPassword().isEmpty()) {
            user.setPassword(encryptPassword(user.getPassword()));
        } else {
            user.setPassword(userService.findById(user.getId()).getPassword());
        }
        userService.update(user);
        String redirectUrl = "";
        if(getUserRole())
        {
            redirectUrl = "redirect:/director/list";
        }
        else{
            redirectUrl = "redirect:/logout";
        }
        return redirectUrl;
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id){
        userService.delete(id);
        return "redirect:/director/list";
    }
    private User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    private String encryptPassword(String plainText) {
        return passwordEncoder.encode(plainText);
    }

    @GetMapping("/view-report")
    public String viewReport(ModelMap modelMap){
        modelMap.addAttribute("academics", academicService.findAll());
        return "/director/view-report";
    }
}

