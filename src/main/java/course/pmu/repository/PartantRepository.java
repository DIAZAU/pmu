package course.pmu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import course.pmu.model.Cheval;
import course.pmu.model.Course;
import course.pmu.model.Partant;
import course.pmu.model.Rang;

public interface PartantRepository extends JpaRepository<Partant, Integer> {

	Partant findByNomAndChevalAndRangAndCourse(String nom, Cheval cheval, Rang rang, Course course);
}
