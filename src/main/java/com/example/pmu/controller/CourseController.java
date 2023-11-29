package com.example.pmu.controller;


import com.example.pmu.dto.CourseDTO;
import com.example.pmu.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course/api/v1")
public class CourseController {

	private final CourseService courseService;


    @PostMapping(value = "/add")
    public ResponseEntity<String> ajout(@Valid @RequestBody CourseDTO course) {
        boolean add = courseService.ajoutCourse(course);
        String message = String.format("Ajout course est en %s", add ? "succès" : "erreur");
        return ResponseEntity.ok(message);
    }
}
