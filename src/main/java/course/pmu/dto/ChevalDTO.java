package course.pmu.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ChevalDTO {
	
	
	private Integer id;
	
	@Valid
	@NotNull(message = "Le nom du cheval du partant ne peut pas être null")
	@NotEmpty(message = "Le nom du cheval du partant ne peut pas être null")
	private String nom;

	private String dateNaissance;

	private String sexe; 

}
