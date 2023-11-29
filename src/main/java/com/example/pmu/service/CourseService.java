package com.example.pmu.service;

import com.example.pmu.dto.CourseDTO;
import com.example.pmu.model.Course;
import com.example.pmu.repository.CourseRepository;
import com.example.pmu.repository.PartantRepository;
import com.example.pmu.service.kafka.PmuProducer;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final PartantRepository partantRepository;
    private final ModelMapper modelMapper;
    private final PmuProducer pmuProducer;

    public boolean ajoutCourse(CourseDTO courseDTO) {
        Course course =  courseRepository.save(modelMapper.map(courseDTO, Course.class));

        if (course != null) {
            pmuProducer.send(modelMapper.map(course, CourseDTO.class));
            return true;
        }
        return false;
    }
}
