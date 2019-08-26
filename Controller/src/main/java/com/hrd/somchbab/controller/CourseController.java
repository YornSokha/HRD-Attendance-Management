package com.hrd.somchbab.controller;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.helper.Validator;
import com.hrd.somchbab.repository.model.*;
import com.hrd.somchbab.service.academic_service.AcademicService;
import com.hrd.somchbab.service.course_service.CourseService;
import com.hrd.somchbab.service.generation_service.GenerationService;
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
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;
    @Autowired
    GenerationService generationService;
    @Autowired
    AcademicService academicService;

    @GetMapping("")
    public String index(@ModelAttribute Course course, ModelMap modelMap,
                       HttpServletRequest httpServletRequest, Paging paging, @RequestParam(defaultValue = "10") Integer limitStatic) {
        paging.setTotalCount(courseService.findAll().size());
        String params = httpServletRequest.getQueryString();
        String[] queries;
        List<String> selectFilters = null;
        if (params != null && !params.contains("page") && !params.contains("lang")) {
            queries = httpServletRequest.getQueryString().split(Pattern.quote("&"));
            selectFilters = getSelectFilters(queries);
        }
        String courseName = course.getName();
        // check if there is a query string to search for student's names
        if (courseName != null) {
            if (!courseName.isEmpty()) {
                setFiltersWithQueryString(modelMap, course, selectFilters, courseName, paging);
            } else {
                setFiltersWithoutQueryString(modelMap, selectFilters, paging);
            }
        } else {
            modelMap.addAttribute("course", new Course());
            modelMap.addAttribute("result", "<span class='h3'>[All]</span>");
            modelMap.addAttribute("courses", courseService.findAllCoursesByPaging(paging));
        }
        // To continue page total number
        if (Paging.limitStatic != 10)
            limitStatic = Paging.limitStatic;
        modelMap.addAttribute("limitStatic", limitStatic);
        modelMap.addAttribute("academics",academicService.findAll());
        return "/course/index";
    }

    private List<String> getSelectFilters(String[] queries) {
        List<String> selectFilters = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            selectFilters.add(queries[i].substring(queries[i].lastIndexOf("=") + 1));
        }
        return selectFilters;
    }

    private void setFiltersWithoutQueryString(ModelMap modelMap, List<String> selectFilters, Paging paging) {
        modelMap.addAttribute("course", new Course());

        // if there is page limit is changed
        if (Validator.isInteger(selectFilters.get(3))) {
            paging.setLimit(Integer.parseInt(selectFilters.get(3)));
        }
        // check if generation is selected
        if (Validator.isInteger(selectFilters.get(1))) {
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 2) + "]</span>");
            modelMap.addAttribute("courses", courseService.filterCoursesByGenerationId(Integer.parseInt(selectFilters.get(1)), paging));
        } else if (Validator.isInteger(selectFilters.get(0))) {
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 1) + "]</span>");
            modelMap.addAttribute("courses", courseService.filterCoursesByAcademicId(Integer.parseInt(selectFilters.get(0)), paging));
        } else {
            modelMap.addAttribute("result", "<span class='h3'>[All]</span>");
            modelMap.addAttribute("courses", courseService.findAllCoursesByPaging(paging));

        }
    }

    // This function is use to generate filter message
    private void setFiltersWithQueryString(ModelMap modelMap, Course course, List<String> selectFilters,
                                           String fullName, Paging paging) {
        modelMap.addAttribute("course", course);
        // check if classroom is selected
        if (Validator.isInteger(selectFilters.get(1))) {
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 2) + "]</span>");
            modelMap.addAttribute("courses", courseService.filterCoursesByNameAndGenerationId(fullName, Integer.parseInt(selectFilters.get(1)), paging));
        } else if (Validator.isInteger(selectFilters.get(0))) {
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 1) + "]</span>");
            modelMap.addAttribute("courses", courseService.filterCoursesByNameAndAcademicId(fullName, Integer.parseInt(selectFilters.get(0)), paging));
        } else {
            modelMap.addAttribute("result",
                    "<span class='h3'>[ALL]</span>");
            modelMap.addAttribute("courses", courseService.findCoursesByName(fullName, paging));
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
        }else {
            return "ALL";
        }
    }

    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.addAttribute("generations", generationService.findAll());
        modelMap.addAttribute("course", new Course());
        return "/course/add";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Course course, BindingResult bindingResult, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("course", course);
            modelMap.addAttribute("generations", generationService.findAll());
            return "/course/add";
        }
        courseService.add(course);
        return "redirect:/course";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable int id, ModelMap modelMap) {
        modelMap.addAttribute("course", courseService.find(id));
        return "/course/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, ModelMap modelMap) {
        modelMap.addAttribute("course", courseService.find(id));
        return "/course/edit";
    }

    @PostMapping("update")
    public String update(@ModelAttribute Course course) {
        courseService.update(course);
        return "redirect:/course";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        courseService.delete(id);
        return "redirect:/course";
    }


}
