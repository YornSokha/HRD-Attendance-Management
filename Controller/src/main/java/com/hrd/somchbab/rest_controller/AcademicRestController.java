package com.hrd.somchbab.rest_controller;

import com.hrd.somchbab.repository.model.Generation;
import com.hrd.somchbab.service.generation_service.GenerationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/academic")
public class AcademicRestController {

    @Autowired
    GenerationService generationService;

    @GetMapping(value = "{id}")
    public Map<String, Object> getGenerationById(@PathVariable("id") int id){
        Map<String, Object> generationMap = new HashMap<>();
        List<Generation> gen = generationService.findByAcademicId(id);
        generationMap.put("data", gen);
        generationMap.put("status", gen != null);
        return generationMap;
    }

    @GetMapping(value = "{academicId}/student/{userId}")
    public Map<String, Object> getGenerationByStudentId(@PathVariable("academicId") int academicId, @PathVariable("userId") int userId){
        Map<String, Object> generationMapOne = new HashMap<>();
        List<Generation> gen = generationService.findByStudentId(academicId, userId);
        generationMapOne.put("data", gen);
        generationMapOne.put("status", gen != null);
        return generationMapOne;
    }
}
