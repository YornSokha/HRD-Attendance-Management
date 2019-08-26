package com.hrd.somchbab.rest_controller;

import com.hrd.somchbab.repository.model.ClassName;
import com.hrd.somchbab.service.classname_service.ClassnameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/course")
public class CourseRestController {

    @Autowired
    ClassnameService classnameService;

    @GetMapping(value = "{id}")
    public Map<String, Object> getClassnameById(@PathVariable("id") int id){
        Map<String, Object> classnameMap = new HashMap<>();
        List<ClassName> classNames = classnameService.findClassNameByID(id);
        classnameMap.put("data", classNames);
        classnameMap.put("status", classNames != null);
        return classnameMap;
    }
}
