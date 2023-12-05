package course.pmu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import course.pmu.model.Simple;
import course.pmu.model.SimplePK;

public interface SimpleRepository extends JpaRepository<Simple, SimplePK> {}
