package com.hrd.somchbab.controller;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.helper.Validator;
import com.hrd.somchbab.repository.model.*;
import com.hrd.somchbab.service.academic_service.AcademicService;
import com.hrd.somchbab.service.classname_service.ClassnameService;
import com.hrd.somchbab.service.classroom_service.ClassroomService;
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
@RequestMapping("/classroom")
public class ClassroomController {
    @Autowired
    ClassroomService classroomService;
    @Autowired
    AcademicService academicService;
    @Autowired
    GenerationService generationService;
    @Autowired
    ClassnameService classnameService;
    @Autowired
    CourseService courseService;

    @SuppressWarnings("Duplicates")
    @GetMapping("")
    public String list(ModelMap modelMap,
                       HttpServletRequest httpServletRequest, Paging paging, @RequestParam(defaultValue = "10") Integer limitStatic) {
        paging.setTotalCount(classroomService.findAllClassrooms().size());
        String params = httpServletRequest.getQueryString();
        String[] queries;
        String classname = null;
        List<String> selectFilters = null;
        if (params != null && !params.contains("page") && !params.contains("lang")) {
            queries = httpServletRequest.getQueryString().split(Pattern.quote("&"));
            selectFilters = getSelectFilters(queries);
            classname = selectFilters.get(3);
        }
        // check if there is a query string to search for student's names
        if (classname != null) {
            if (!classname.isEmpty()) {
                setFiltersWithQueryString(modelMap, selectFilters, classname, paging);
            } else {
                setFiltersWithoutQueryString(modelMap, selectFilters, paging);
            }
        } else {
            modelMap.addAttribute("classroom", new Classroom());
            modelMap.addAttribute("result", "<span class='h3'>[All]</span>");
            modelMap.addAttribute("classrooms", classroomService.findAllClassroomsByPaging(paging));
        }
        // To continue page total number
        if (Paging.limitStatic != 10)
            limitStatic = Paging.limitStatic;
        modelMap.addAttribute("limitStatic", limitStatic);
        modelMap.addAttribute("academics", academicService.findAll());
        return "/classroom/index";
    }


    private void setFiltersWithoutQueryString(ModelMap modelMap, List<String> selectFilters, Paging paging) {
        modelMap.addAttribute("classroom", new Classroom());

        // if there is page limit is changed
        if (Validator.isInteger(selectFilters.get(4))) {
            paging.setLimit(Integer.parseInt(selectFilters.get(4)));
        }
        // check if classroom is selected
        if (Validator.isInteger(selectFilters.get(2))) {
            modelMap.addAttribute("result","<span class='h3'>[" + getFilterResults(selectFilters, 3) + "]</span>");
            modelMap.addAttribute("classrooms", classroomService.filterClassroomsByCourseId(Integer.parseInt(selectFilters.get(2)), paging));
        } else if (Validator.isInteger(selectFilters.get(1))) {
            modelMap.addAttribute("result","<span class='h3'>[" + getFilterResults(selectFilters, 2) + "]</span>");
            modelMap.addAttribute("classrooms", classroomService.filterClassroomsByGenerationId(Integer.parseInt(selectFilters.get(1)), paging));
        } else if (Validator.isInteger(selectFilters.get(0))) {
            modelMap.addAttribute("result","<span class='h3'>[" + getFilterResults(selectFilters, 1) + "]</span>");
            modelMap.addAttribute("classrooms", classroomService.filterClassroomsByAcademicId(Integer.parseInt(selectFilters.get(0)), paging));
        } else {
            modelMap.addAttribute("user", new User());
            modelMap.addAttribute("result", "<span class='h3'>[All]</span>");
            modelMap.addAttribute("classrooms", classroomService.findAllClassroomsByPaging(paging));

        }
    }

    // This function is use to generate filter message
    private void setFiltersWithQueryString(ModelMap modelMap, List<String> selectFilters,
                                           String fullName, Paging paging) {
        modelMap.addAttribute("classroom", new Classroom(fullName));
        // check if classroom is selected
        if (Validator.isInteger(selectFilters.get(2))) {
            modelMap.addAttribute("result","<span class='h3'>[" + getFilterResults(selectFilters, 3) + "]</span>");
            modelMap.addAttribute("classrooms", classroomService.filterClassroomsByNameAndCourseId(fullName, Integer.parseInt(selectFilters.get(2)), paging));
        } else if (Validator.isInteger(selectFilters.get(1))) {
            modelMap.addAttribute("result","<span class='h3'>[" + getFilterResults(selectFilters, 2) + "]</span>");
            modelMap.addAttribute("classrooms", classroomService.filterClassroomsByNameAndGenerationId(fullName, Integer.parseInt(selectFilters.get(1)), paging));
        } else if (Validator.isInteger(selectFilters.get(0))) {
            modelMap.addAttribute("result","<span class='h3'>[" + getFilterResults(selectFilters, 1) + "]</span>");
            modelMap.addAttribute("classrooms", classroomService.filterClassroomsByNameAndAcademicId(fullName, Integer.parseInt(selectFilters.get(0)), paging));
        } else {
            modelMap.addAttribute("result","<span class='h3'>[ALL]</span>");
            modelMap.addAttribute("classrooms", classroomService.findClassroomsByName(fullName, paging));
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
        } else if (idsFilter.length == 3) {
            return academicService.findById(idsFilter[0]).getName() +
                    ">" + generationService.findById(idsFilter[1]).getName() +
                    ">" + courseService.find(idsFilter[2]).getName();
        } else if (idsFilter.length == 4) {
            return academicService.findById(idsFilter[0]).getName() +
                    ">" + generationService.findById(idsFilter[1]).getName() +
                    ">" + courseService.find(idsFilter[2]).getName();
        }else {
            return "ALL";
        }
    }
    private List<String> getSelectFilters(String[] queries) {
        List<String> selectFilters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            selectFilters.add(queries[i].substring(queries[i].lastIndexOf("=") + 1));
        }
        return selectFilters;
    }

    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.addAttribute("classroom", new Classroom());
        modelMap.addAttribute("classnames", classnameService.findAll());
        modelMap.addAttribute("academics", academicService.findAll());
        return "/classroom/add";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Classroom classroom, BindingResult bindingResult, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("classroom", classroom);
            modelMap.addAttribute("classnames", classnameService.findAll());
            modelMap.addAttribute("academics", academicService.findAll());
            return "/classroom/add";
        }
        classroomService.add(classroom);
        return "redirect:/classroom";
    }

    @GetMapping("/view/{id}")
    public String view(ModelMap modelMap, @PathVariable Integer id) {
        modelMap.addAttribute("classroom", classroomService.findByID(id));
        return "classroom/view";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, ModelMap modelMap) {
        modelMap.addAttribute("classroom", classroomService.findByID(id));
        return "/classroom/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Classroom classroom) {
        classroomService.update(classroom);
        return "redirect:/classroom";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        classroomService.delete(id);
        return "redirect:/classroom";
    }
}
