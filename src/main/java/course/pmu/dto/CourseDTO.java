package course.pmu.dto;

import java.time.Instant;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class CourseDTO {

	private Integer CourseId;
	
	@Valid
	@NotNull(message = "Le lieu d'une course ne peut pas être null")
	private LieuDTO lieu;
	
    @NotNull(message = "Le date d'une course ne peut pas être null")
    private Instant jour;

    @NotNull(message = "Le nom d'une course ne peut pas être null")
    @NotEmpty(message = "Le nom d'une course ne peut pas être vide")
    private String nom;
    
    @NotNull(message = "Le numéro d'une course ne peut pas être null")
    @NotEmpty(message = "Le numéro d'une course ne peut pas être vide")
    private String numero;
   
    @Valid
    @NotNull(message = "Les participants d'une course ne peuvent pas être null")
    @NotEmpty(message = "Les participants d'une course ne peuvent pas être vides")
    @Size(min = 3, message = "Une course possède au moins 3 partants")
    private List<PartantDTO> partants;
}
