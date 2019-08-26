package com.hrd.somchbab.controller;

import com.hrd.somchbab.service.dashboard_service.DashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("")
    public String index(ModelMap modelMap){
        modelMap.addAttribute("requestingPermission", dashboardService.findRequestingPermission());
        modelMap.addAttribute("requestedPermission", dashboardService.findRequestedPermission());
        modelMap.addAttribute("mostLate", dashboardService.findMostLate());
        return "/dashboard/index";
    }
}
