package course.pmu.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Data
@Builder
@ToString
public class LieuDTO {
	
	private Integer id;

	@NotNull(message = "Le terrain de la course ne peut pas être null")
	@NotEmpty(message = "Le terrain de la course ne peut pas être vide")
	private String hyppodrome;

	
	@NotNull(message = "L'hypodrome de la course ne peut pas être null")
    @NotEmpty(message = "L'hypodrome de la course ne peut pas être vide")
	private String terrain;
}
