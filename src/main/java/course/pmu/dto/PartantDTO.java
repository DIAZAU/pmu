package course.pmu.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Data
@Builder
@ToString
public class PartantDTO {
	
	private Integer id;
	
	@NotNull(message = "Le nom d'un partant ne peut pas être null")
	@NotEmpty(message = "Le nom d'un partant ne peut pas être vide")
	private String nom;
	
	@Valid
    @NotNull(message = "Le numéro d'un partant ne peut pas être null")
    private RangDTO rang;
    
	@Valid
    @NotNull(message = "Le cheval d'un partant ne peut pas être null")
    private ChevalDTO cheval;
}
