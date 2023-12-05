package course.pmu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

/**
 * The persistent class for the cheval database table.
 * 
 */
@Data
@Entity
@Table(name = "cheval", 
uniqueConstraints = { @UniqueConstraint(name = "cheval_nom_const", columnNames = { "chevalNom" })
})
public class Cheval implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "chevalId", unique=true, nullable=false)
	private Integer id;

	@Column(name = "chevalNom", nullable=false, length=48)
	private String nom;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "chevalDateNaissance")
	private Date dateNaissance;

	@Column(name = "chevalSexe")
	private String sexe;

	//bi-directional many-to-one association to Partant
	@OneToMany(mappedBy="cheval")
	private List<Partant> partants;

}