package course.pmu.reponse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseApi<T> {

	@JsonProperty("valeur")
    private final T valeur;
	
	@JsonProperty("code")
    private final String code;
	
	@JsonProperty("erreurs")
    private final List<ErreurApi> erreurs;
	
	@JsonProperty("message")
    private final Object message;
	
	

}
