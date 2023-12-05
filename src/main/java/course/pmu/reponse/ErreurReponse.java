package course.pmu.reponse;
import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ErreurReponse implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("message")
    private String message;
    @JsonProperty("code")
    private HttpStatus code;
    @JsonProperty("erreur")
    private String erreur;

}

