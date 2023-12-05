package course.pmu.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RangDTO {
	
	private Integer id;
	
	@Valid
    @NotNull(message = "Le numéro d'un rang ne peut pas être null")
    private int numero;
}
