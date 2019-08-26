package com.hrd.somchbab.controller;

import com.hrd.somchbab.service.classname_service.ClassnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/get-start")
public class GetStartedController {
    @Autowired
ClassnameService classnameService;
    @GetMapping("")
    public String index(ModelMap modelMap){
        modelMap.addAttribute("classnames",classnameService.findAll());
        return "/get_started/index";
    }
}
