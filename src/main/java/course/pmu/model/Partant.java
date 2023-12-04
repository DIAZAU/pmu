package course.pmu.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

/**
 * The persistent class for the partant database table.
 * 
 */
@Data
@Entity
@Table(name = "partant", uniqueConstraints = {
		@UniqueConstraint(name = "partant_const", columnNames = { "courseId", "chevalId", "rangId" }) })
public class Partant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "partantId")
	private Integer id;

	@Column(name = "partantNom", nullable = false, length = 16)
	private String nom;
	
	// bi-directional many-to-one association to Cheval
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "chevalId", nullable = false)
	private Cheval cheval;

	// bi-directional many-to-one association to Rang
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "rangId")
	private Rang rang;

	// bi-directional many-to-one association to CoursePojo
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "courseId", nullable = false)
	private Course course;

	@Column(name = "partantCommentaire")
	private String commentaire;

	@Column(name = "partantPoids")
	private BigDecimal poids;

}