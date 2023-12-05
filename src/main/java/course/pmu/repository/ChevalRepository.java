package course.pmu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import course.pmu.model.Cheval;

public interface ChevalRepository extends JpaRepository<Cheval, Integer> {

	Cheval findByNom(String nom);}
