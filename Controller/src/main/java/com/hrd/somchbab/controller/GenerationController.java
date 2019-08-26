package com.hrd.somchbab.controller;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.helper.Validator;
import com.hrd.somchbab.repository.model.Generation;
import com.hrd.somchbab.service.academic_service.AcademicService;
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
@RequestMapping("/generation")
public class GenerationController {
    @Autowired
    GenerationService generationService;
    @Autowired
    AcademicService academicService;

    @GetMapping("")
    public String index(@ModelAttribute Generation generation, ModelMap modelMap,
                        HttpServletRequest httpServletRequest, Paging paging, @RequestParam(defaultValue = "10") Integer limitStatic) {
        paging.setTotalCount(generationService.findAll().size());
        String params = httpServletRequest.getQueryString();
        String[] queries;
        List<String> selectFilters = null;
        if (params != null && !params.contains("page") && !params.contains("lang")) {
            queries = httpServletRequest.getQueryString().split(Pattern.quote("&"));
            selectFilters = getSelectFilters(queries);
        }
        String generationName = generation.getName();
        // check if there is a query string to search for student's names
        if (generationName != null) {
            if (!generationName.isEmpty()) {
                setFiltersWithQueryString(modelMap, generation, selectFilters, generationName, paging);
            } else {
                setFiltersWithoutQueryString(modelMap, selectFilters, paging);
            }
        } else {
            modelMap.addAttribute("generation", new Generation());
            modelMap.addAttribute("result", "<span class='h3'>[All]</span>");
            modelMap.addAttribute("generations", generationService.findAllGenerationsByPaging(paging));
        }
        // To continue page total number
        if (Paging.limitStatic != 10)
            limitStatic = Paging.limitStatic;
        modelMap.addAttribute("limitStatic", limitStatic);
        modelMap.addAttribute("academics", academicService.findAll());
        return "/generation/index";
    }

    private List<String> getSelectFilters(String[] queries) {
        List<String> selectFilters = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            selectFilters.add(queries[i].substring(queries[i].lastIndexOf("=") + 1));
        }
        return selectFilters;
    }

    private void setFiltersWithoutQueryString(ModelMap modelMap, List<String> selectFilters, Paging paging) {
        modelMap.addAttribute("generation", new Generation());

        // if there is page limit is changed
        if (Validator.isInteger(selectFilters.get(2))) {
            paging.setLimit(Integer.parseInt(selectFilters.get(2)));
        }
        // check if classroom is selected
        if (Validator.isInteger(selectFilters.get(0))) {
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 1) + "]</span>");
            modelMap.addAttribute("generations", generationService.filterGenerationsByAcademicId(Integer.parseInt(selectFilters.get(0)), paging));
        } else {
            modelMap.addAttribute("result", "<span class='h3'>[All]</span>");
            modelMap.addAttribute("generations", generationService.findAllGenerationsByPaging(paging));

        }
    }

    // This function is use to generate filter message
    private void setFiltersWithQueryString(ModelMap modelMap, Generation generation, List<String> selectFilters,
                                           String fullName, Paging paging) {
        modelMap.addAttribute("generation", generation);
        // check if classroom is selected
        if (Validator.isInteger(selectFilters.get(0))) {
            modelMap.addAttribute("result",
                    "<span class='h3'>[" + getFilterResults(selectFilters, 1) + "]</span>");
            modelMap.addAttribute("generations", generationService.filterGenerationsByNameAndAcademicId(fullName, Integer.parseInt(selectFilters.get(0)), paging));
        } else {
            modelMap.addAttribute("result",
                    "<span class='h3'>[ALL]</span>");
            modelMap.addAttribute("generations", generationService.findGenerationsByName(fullName, paging));
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
        }else {
            return "ALL";
        }
    }

    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.addAttribute("academics", academicService.findAll());
        modelMap.addAttribute("generation", new Generation());
        return "/generation/add";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Generation generation,
                         BindingResult bindingResult, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("generation", generation);
            modelMap.addAttribute("academics", academicService.findAll());
            return "/generation/add";
        }
        generationService.add(generation);
        return "redirect:/generation";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable int id, ModelMap modelMap) {
        modelMap.addAttribute("generation", generationService.findById(id));
        return "/generation/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, ModelMap modelMap) {
        modelMap.addAttribute("generation", generationService.findById(id));
        return "/generation/edit";
    }

    @PostMapping("update")
    public String update(@ModelAttribute Generation generation) {
        generationService.update(generation);
        return "redirect:/generation";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        generationService.delete(id);
        return "redirect:/generation";
    }
}
