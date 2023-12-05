package course.pmu.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErreurApi {

	@JsonProperty("typeErreur")
	private final TypeErreur typeErreur;

	@JsonProperty("identifiant")
	private Object identifiant;

	@JsonProperty("valeur")
	private Object valeur;

	public enum TypeErreur {
		HTTP,
		ERREUR,
		DONNEE_INCORRECTE,
		DOUBLON, 
		INEXISTANT;
	}

}
