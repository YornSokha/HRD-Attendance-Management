package com.hrd.somchbab.rest_controller;

import com.hrd.somchbab.repository.model.ClassName;
import com.hrd.somchbab.repository.model.Classroom;
import com.hrd.somchbab.repository.model.Course;
import com.hrd.somchbab.repository.model.Generation;
import com.hrd.somchbab.service.classname_service.ClassnameService;
import com.hrd.somchbab.service.classroom_service.ClassroomService;
import com.hrd.somchbab.service.course_service.CourseService;
import com.hrd.somchbab.service.generation_service.GenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("api/get-start/")
public class GetStartRestController {
    @Autowired
    GenerationService generationService;
    @Autowired
    CourseService courseService;
    @Autowired
    ClassroomService classroomService;
    @Autowired
    ClassnameService classnameService;

    @ResponseBody
    @PostMapping("generation")
    public int generation(@RequestBody final Generation generation) {
        if (!generation.getName().isEmpty()){
            generationService.addAPI(generation);
            return generationService.getLastId();
        }else
            return 0;
    }
    @PostMapping("course")
    public int course(@RequestBody final Course course) {
        if(!course.getName().isEmpty()){
            courseService.add(course);
            return courseService.getLastId();
        }else
            return 0;
    }
    @PostMapping("classroom")
    public int classroom(@RequestBody final Classroom classroom) {
        if (classroom.getClassName().getId()!=0){
            classroomService.add(classroom);
            return generationService.getLastId();
        }else
            return 0;
    }
}
