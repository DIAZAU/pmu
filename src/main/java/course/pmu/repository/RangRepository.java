package course.pmu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import course.pmu.model.Rang;

public interface RangRepository extends JpaRepository<Rang, Integer> {

	Rang findByNumero(int numero);
}
