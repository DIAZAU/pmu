package course.pmu.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;


/**
 * The persistent class for the rang database table.
 * 
 */
@Data
@Entity
@Table(name="rang", 
uniqueConstraints = { @UniqueConstraint(name = "rang_numero_const", columnNames = { "rangNumero" })
})
public class Rang implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "rangId", unique=true, nullable=false)
	private Integer id;

	@Column(name = "rangNumero", nullable=false)
	private int numero;

	//bi-directional many-to-one association to Partant
	@OneToMany(mappedBy="rang")
	private List<Partant> partants;

}