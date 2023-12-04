package course.pmu.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import course.pmu.commun.Etat;
import course.pmu.dto.ChevalDTO;
import course.pmu.dto.CourseDTO;
import course.pmu.dto.LieuDTO;
import course.pmu.dto.PartantDTO;
import course.pmu.dto.RangDTO;
import course.pmu.model.Cheval;
import course.pmu.model.Course;
import course.pmu.model.Lieu;
import course.pmu.model.Partant;
import course.pmu.model.Rang;
import course.pmu.model.Simple;
import course.pmu.reponse.ErreurApi;
import course.pmu.reponse.ErreurApi.TypeErreur;
import course.pmu.reponse.ResponseApi;
import course.pmu.repository.ChevalRepository;
import course.pmu.repository.CourseRepository;
import course.pmu.repository.LieuRepository;
import course.pmu.repository.PartantRepository;
import course.pmu.repository.RangRepository;
import course.pmu.repository.SimpleRepository;
import course.pmu.service.kafka.PmuProducer;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class CourseService {

	@Autowired
	private final CourseRepository courseRepository;

	@Autowired
	private final PartantRepository partantRepository;

	@Autowired
	private final ChevalRepository chevalRepository;

	@Autowired
	private final RangRepository rangRepository;

	@Autowired
	private final LieuRepository lieuRepository;

	@Autowired
	private final SimpleRepository simpleRepository;

	@Autowired
	private final ModelMapper modelMapper;

	@Autowired
	private final PmuProducer pmuProducer;

	public ResponseApi<Integer> ajoutCourse(CourseDTO courseDTO) {
		
		List<ErreurApi> erreurs = new ArrayList<ErreurApi>();
		
		Course course = null;
		Lieu lieu = null;
		if (estValide(courseDTO)) {
			
			lieu = ajoutLieu(courseDTO.getLieu());
			if (lieu != null ) {
				course = modelMapper.map(courseDTO, Course.class);
				if (course != null) {
					course.setLieu(lieu);
					course = courseRepository.save(course);
					ajoutPartants(courseDTO, course);
				}
				if (estValide(course)) {
					ajouCourseSimple(course);
					pmuProducer.send(modelMapper.map(course, CourseDTO.class));
					return new ResponseApi<>(course.getId(), Etat.OK.name(), erreurs, "Ajout course effectué avec succes.");
				}
				else {
					erreurs.add(new ErreurApi(TypeErreur.DONNEE_INCORRECTE, "course", courseDTO.getLieu().toString()));
				}
			}else {
				erreurs.add(new ErreurApi(TypeErreur.DONNEE_INCORRECTE, "lieu", modelMapper.map(course, CourseDTO.class)));
			}
			
		}else {
			erreurs.add(new ErreurApi(TypeErreur.DONNEE_INCORRECTE, "courseDTO", courseDTO.toString()));
		}
		return new ResponseApi<>( null, Etat.KO.name(), erreurs, "Ajout course en erreurs.");
	}

	private void ajouCourseSimple(Course course) {
		Simple coursesimple = new Simple();
		course.getPartants().forEach(partant -> {
			
			switch (partant.getRang().getNumero()) {
			
			case 1:
				coursesimple.setPartant1(1);
				break;
				
			case 2:
				coursesimple.setPartant2(2);
				break;
			case 3:
				coursesimple.setPartant3(3);
				break;
				
			default:
				return;
			}
		});
		coursesimple.setCourse(course);
		simpleRepository.save(coursesimple);
	}

	private void ajoutPartants(CourseDTO courseDTO, Course course) {
		if(course != null) {
			course.setPartants(new ArrayList<Partant>());
			for (PartantDTO partantDTO : courseDTO.getPartants()) {
				ajoutPartant(partantDTO, course);
			}
		}
	}

	private Partant ajoutPartant(PartantDTO partantDTO, Course course) {
		Partant partant = null ;
		if (estValide(partantDTO) && course != null){
			
			if(partantDTO.getId() != null) {
				return partantRepository.findById(partantDTO.getId()).orElseGet(null);
			}
			else {
				
				Cheval cheval =  ajoutCheval(partantDTO.getCheval());
				Rang rang =  ajoutRang(partantDTO.getRang());
				
				if(cheval != null && rang != null) {
					partant = partantRepository.findByNomAndChevalAndRangAndCourse(partantDTO.getNom(), cheval, rang, course);
					if(partant == null) {
						partant = modelMapper.map(partantDTO, Partant.class);
						partant.setCheval(cheval);
						partant.setRang(rang);
						partant.setCourse(course);
						partant = partantRepository.save(partant);
						if(partant != null) {
							course.getPartants().add(partant);
						}
					}
					return partant;
				}
			}
		}
		return null;
	}
	
	private Rang ajoutRang(RangDTO rangDTO) {
		Rang rang = null;
		if (estValide(rangDTO)) {
			
			if(rangDTO.getId() != null) {
				return rangRepository.findById(rangDTO.getId()).orElseGet(null);
			} else {
				rang = rangRepository.findByNumero(rangDTO.getNumero());
				if (rang == null) {
					return rangRepository.save(modelMapper.map(rangDTO, Rang.class));
				}
				return rang;
			}
		}
		return rang;
	}

	private Cheval ajoutCheval(ChevalDTO chevalDTO) {
		if (estValide(chevalDTO)) {
			if(chevalDTO.getId() != null) {
				return chevalRepository.findById(chevalDTO.getId()).orElseGet(null);
				
			} else {
				Cheval cheval  = chevalRepository.findByNom(chevalDTO.getNom());
				if (cheval == null) {
					return chevalRepository.save(modelMapper.map(chevalDTO, Cheval.class));
				}
				return cheval;
			}
		}
		return null;
	}

	private Lieu ajoutLieu(LieuDTO lieuDTO) {
		if (estValide(lieuDTO)) {
			if(lieuDTO.getId() != null) {
				
				return lieuRepository.findById(lieuDTO.getId()).orElseGet(null);
				
			} else {
				
				Lieu lieu = lieuRepository.findByHyppodromeAndTerrain(lieuDTO.getHyppodrome(), lieuDTO.getTerrain());
				if (lieu == null) {
					return lieuRepository.save(modelMapper.map(lieuDTO, Lieu.class));
				}
				return lieu;
			}
		}
		return null;
	}
	
	private boolean estValide(CourseDTO courseDTO) {
		return courseDTO != null 
				&& courseDTO.getJour() != null
				&& estValide(courseDTO.getLieu())
				&& estValide(courseDTO.getPartants());
	}

	private boolean estValide(List<PartantDTO> partants) {
		return partants != null 
				&& partants.size() >= 3 
				&& partants.stream().allMatch(partant -> estValide(partant))
				&& rechercherDoublons(
						partants.stream()
						.map(partant -> partant.getRang().getNumero())
						.collect(Collectors.toList())).isEmpty()
				&& estValideNumeros(
						partants.stream()
						.map(partant -> partant.getRang().getNumero())
						.collect(Collectors.toList()));
	}

	private boolean estValide(LieuDTO lieu) {
		return lieu != null 
				&& StringUtils.isNotEmpty(lieu.getHyppodrome()) 
				&& StringUtils.isNotEmpty(lieu.getTerrain());
	}
	
	boolean estValide(PartantDTO partant) {
		return estValide(partant.getCheval()) 
				&& estValide(partant.getRang()) 
				&& StringUtils.isNotEmpty(partant.getNom());
	}
	
	private boolean estValide(RangDTO rang) {
		return rang != null 
				&& rang.getNumero() > 0;
	}

	private boolean estValide(ChevalDTO cheval) {
		return cheval != null 
				&& StringUtils.isNotEmpty(cheval.getNom());
	}
	
	private boolean estValide(Course course) {
		return course != null 
				&& course.getJour() != null
				&& course.getLieu() != null
				&& course.getPartants() != null 
				&& course.getPartants().size() >= 3;
	}
	
	private boolean estValideNumeros(List<Integer> numerosPartants) {
		if(numerosPartants != null) {
			List<Integer> numeros = new ArrayList<Integer>();
			for(Integer i = 0; i < numerosPartants.size() ; i++) {
				numeros.add(i+1);
			}
			return numerosPartants.containsAll(numeros);
		}
		return false;
	}
	
	private static <T> Set<T> rechercherDoublons(List<T> list)
    {
        Set<T> seen = new HashSet<>();
        return list.stream()
                .filter(e -> !seen.add(e))
                .collect(Collectors.toSet());
    }
}
