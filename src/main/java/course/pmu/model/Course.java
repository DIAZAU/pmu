package course.pmu.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "course", 
	uniqueConstraints = { @UniqueConstraint(name = "course_const", columnNames = { "lieuId", "CourseJour", "courseNumero" })
})
public class Course implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "courseId")
	private Integer id;

	// bi-directional many-to-one association to Lieu
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="lieuId", nullable = false)
	private Lieu lieu;
	
	@Column(name = "CourseJour", nullable = false, length = 256)
	private Instant jour;


	@Column(name = "courseNom", nullable = false, length = 256)
	private String nom;

	@Column(name = "courseNumero", nullable = false)
	private String numero;

	// bi-directional many-to-one association to Partant
	@OneToMany(mappedBy = "course")
	private List<Partant> partants;

}
