package com.hrd.somchbab.controller;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.helper.Validator;
import com.hrd.somchbab.repository.model.Academic;
import com.hrd.somchbab.repository.model.Generation;
import com.hrd.somchbab.repository.model.User;
import com.hrd.somchbab.service.academic_service.AcademicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/academic")
public class AcademicController {

    @Autowired
    AcademicService academicService;

    @GetMapping("")
    public String index(@ModelAttribute Academic academic, ModelMap modelMap, HttpServletRequest httpServletRequest, Paging paging, @RequestParam(defaultValue = "10") Integer limitStatic) {
        paging.setTotalCount(academicService.findAll().size());
        String params = httpServletRequest.getQueryString();
        String[] queries;
        List<String> selectFilters = null;
        if (params != null && !params.contains("page") && !params.contains("lang")) {
            queries = httpServletRequest.getQueryString().split(Pattern.quote("&"));
            selectFilters = getSelectFilters(queries);
        }
        String academinName = academic.getName();
        // check if there is a query string to search for generationName's names

        if (academinName != null) {
            if (!academinName.isEmpty()) {
                modelMap.addAttribute("academic",academic);
                modelMap.addAttribute("academics", academicService.findByName(academinName,paging));
            } else {
                if (Validator.isInteger(selectFilters.get(1))) {
                    paging.setLimit(Integer.parseInt(selectFilters.get(1)));;;
                }
                modelMap.addAttribute("academic",new Academic());
                modelMap.addAttribute("academics", academicService.findAllAcademicByPanging(paging));
            }
        } else {
            modelMap.addAttribute("academic",new Academic());
            modelMap.addAttribute("academics", academicService.findAllAcademicByPanging(paging));
        }
        // To continue page total number
        if (Paging.limitStatic != 10)
            limitStatic = Paging.limitStatic;
        modelMap.addAttribute("limitStatic", limitStatic);
        return "/academic/index";
    }

    private List<String> getSelectFilters(String[] queries) {
        List<String> selectFilters = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            selectFilters.add(queries[i].substring(queries[i].lastIndexOf("=") + 1));
        }
        return selectFilters;
    }

    @GetMapping("/add")
    public String add(ModelMap modelMap){
        modelMap.addAttribute("academic", new Academic());
        return "/academic/add";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Academic academic, BindingResult bindingResult, ModelMap modelMap){
        if(bindingResult.hasErrors()){
            modelMap.addAttribute("academic", academic);
            return "/academic/add";
        }
        academicService.add(academic);
        return "redirect:/academic";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable int id, ModelMap modelMap){
        modelMap.addAttribute("academic", academicService.findById(id));
        return "/academic/view";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable int id, ModelMap  modelMap){
        modelMap.addAttribute("academic", academicService.findById(id));
        return "/academic/edit";
    }

    @PostMapping("update")
    public String update(@ModelAttribute Academic academic){
        academicService.update(academic);
        return "redirect:/academic";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        academicService.delete(id);
        return "redirect:/academic";
    }
}
