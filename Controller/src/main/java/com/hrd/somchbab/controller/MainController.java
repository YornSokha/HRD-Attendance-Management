package com.hrd.somchbab.controller;

import com.hrd.somchbab.service.academic_service.AcademicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    AcademicService academicService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("@currentUserServiceImpl.adminAndDirectorAccess(principal)")
    @GetMapping("/")
    public String dashboard(){
        return "dashboard/index";
    }

    @GetMapping("/bc/{password}")
    @ResponseBody
    public String getPassword(@PathVariable String password) {
        return passwordEncoder.encode(password);
    }
}
