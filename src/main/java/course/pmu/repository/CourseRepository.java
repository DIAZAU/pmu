package course.pmu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import course.pmu.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {}
