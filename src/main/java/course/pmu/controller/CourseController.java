package course.pmu.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course.pmu.dto.CourseDTO;
import course.pmu.reponse.ResponseApi;
import course.pmu.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
public class CourseController {
	
	@Autowired
	private final CourseService courseService;

    @PostMapping(value = "/add")
    public ResponseApi<Integer> ajout(@Valid @RequestBody CourseDTO course) {
       return courseService.ajoutCourse(course);
       
    }
}
