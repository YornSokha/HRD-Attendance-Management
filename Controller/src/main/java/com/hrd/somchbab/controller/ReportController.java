package com.hrd.somchbab.controller;

import com.hrd.somchbab.service.academic_service.AcademicService;
import com.hrd.somchbab.service.report_service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    AcademicService academicService;

    @Autowired
    ReportService reportService;

    @GetMapping("")
    public String index(ModelMap modelMap){
        modelMap.addAttribute("academics", academicService.findAll());
        return "/report/index";
    }
}
