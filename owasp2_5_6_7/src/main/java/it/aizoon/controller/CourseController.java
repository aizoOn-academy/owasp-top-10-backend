package it.aizoon.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.aizoon.dto.JsonDto;
import it.aizoon.model.Course;
import it.aizoon.service.CourseService;

@Controller
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Course> courseList = (List<Course>) courseService.findAllCourses();
        model.addAttribute("courses", courseList.isEmpty() ? Collections.EMPTY_LIST : courseList);
        return "index";
    }

    @GetMapping("/addcourse")
    public String showAddCourseForm(Course course) {
        return "add-course";
    }

    //TODO try <script>alert('ciao!')</script>
    @PostMapping("/addcourse")
    public String addCourse(@Valid Course course, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "add-course";
        }
        courseService.createCourse(course);
        model.addAttribute("courses", courseService.findAllCourses());
        return "redirect:/index";
    }

    @GetMapping("/update/{id}")
    public String showUpdateCourseForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("course", courseService.findCourseById(id).get());
        return "update-course";
    }

    @PutMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") Long id, @Valid Course course, BindingResult result, Model model) {
        if (result.hasErrors()) {
            course.setId(id);
            return "update-course";
        }
        courseService.updateCourse(course);
        model.addAttribute("courses", courseService.findAllCourses());
        return "redirect:/index";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id, Model model) {
        courseService.deleteCourseById(id);
        model.addAttribute("courses", courseService.findAllCourses());
        return "redirect:/index";
    }

    @GetMapping("/message")
    @ResponseBody
    public JsonDto getMessage() {
        JsonDto jsonDto = new JsonDto();
        if (true) {
            throw new RuntimeException("errore!");
        }
        jsonDto.setMessage("hello world!");
        return jsonDto;
    }
}

