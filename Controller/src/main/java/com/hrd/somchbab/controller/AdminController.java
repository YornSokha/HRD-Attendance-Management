package com.hrd.somchbab.controller;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.helper.UploadImage;
import com.hrd.somchbab.helper.Validator;
import com.hrd.somchbab.repository.model.Academic;
import com.hrd.somchbab.repository.model.User;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    DashboardService dashboardService;

    @Value("${file.server.path}")
    private String serverPath;

    private boolean image;

    @GetMapping("")
    public String index() {
        return "director/home";
    }
    public String home(ModelMap modelMap){
        modelMap.addAttribute("requestingPermission", dashboardService.findRequestingPermission());
        modelMap.addAttribute("requestedPermission", dashboardService.findRequestedPermission());
        modelMap.addAttribute("mostLate", dashboardService.findMostLate());
        return "/director/home";
    }

    @GetMapping("/list")
    public String index(@ModelAttribute User user, ModelMap modelMap, HttpServletRequest httpServletRequest, Paging paging, @RequestParam(defaultValue = "10") Integer limitStatic) {
        paging.setTotalCount(userService.findAllByRoleName("ADMIN").size());
        String params = httpServletRequest.getQueryString();
        String[] queries;
        List<String> selectFilters = null;
        if (params != null && !params.contains("page") && !params.contains("lang")) {
            queries = httpServletRequest.getQueryString().split(Pattern.quote("&"));
            selectFilters = getSelectFilters(queries);
        }
        String adminName = user.getFullName();
        // check if there is a query string to search for generationName's names

        if (adminName != null) {
            if (!adminName.isEmpty()) {
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("admins",userService.findAdminsByNameAndPaging(adminName,paging));
            } else {
                if (Validator.isInteger(selectFilters.get(1))) {
                    paging.setLimit(Integer.parseInt(selectFilters.get(1)));
                }
                modelMap.addAttribute("user", new User());
                modelMap.addAttribute("admins", userService.findAllAdminByPaging(paging));
            }
        } else {
            modelMap.addAttribute("user", new User());
            modelMap.addAttribute("admins", userService.findAllAdminByPaging(paging));
        }
        // To continue page total number
        if (Paging.limitStatic != 10)
            limitStatic = Paging.limitStatic;
        modelMap.addAttribute("limitStatic", limitStatic);
        return "/admin/list";
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
        modelMap.addAttribute("admin",userService.findById(id));
        return "/admin/view";
    }

    @GetMapping("/add")
    public String add(ModelMap modelMap){
        modelMap.addAttribute("admin",new User());
        return "/admin/add";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute User user, BindingResult bindingResult, MultipartFile file, ModelMap modelMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        user.setCreatedBy(((User) authentication.getPrincipal()).getId());
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("admin", user);
            return "/admin/add";
        }
        new UploadImage().upload(user, file, serverPath);
        user.setPassword("$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO."); // = 123
        userService.add(user);
        int userId = userService.getLastId();
        userService.addRoleToUser(userId, 2);
        return "redirect:/admin/list";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, ModelMap modelMap){
        modelMap.addAttribute("admin", userService.findById(id));
        return "/admin/edit";
    }

    @PostMapping("update")
    public String update(@ModelAttribute User user,BindingResult bindingResult,@RequestParam MultipartFile file, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("admin", user);
            return "/admin/add";
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        String redirectUrl = "";
        if(hasUserRole)
        {
            redirectUrl = "redirect:/admin/list";
        }
        else{
            redirectUrl = "redirect:/logout";
        }
        return redirectUrl;
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id){
        userService.delete(id);
        return "redirect:/admin/list";
    }
    private String encryptPassword(String plainText) {
        return passwordEncoder.encode(plainText);
    }

}
