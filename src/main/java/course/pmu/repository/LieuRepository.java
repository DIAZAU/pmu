package course.pmu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import course.pmu.model.Lieu;

public interface LieuRepository extends JpaRepository<Lieu, Integer> {

	Lieu findByHyppodromeAndTerrain(String hyppodrome, String terrain);}
