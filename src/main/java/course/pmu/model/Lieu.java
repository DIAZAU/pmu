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

@Data
@Entity
@Table(name="lieu" , 
	uniqueConstraints = { 
			@UniqueConstraint(name = "lieu_const", columnNames = { "LieuHippodrome", "LieuTerrain" })
})
public class Lieu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "lieuId", unique=true, nullable=false)
	private Integer id;

	@Column(name = "LieuHippodrome", nullable=false, length=256)
	private String hyppodrome;
	
	@Column(name = "LieuTerrain",nullable=false, length=256)
	private String terrain;


	//bi-directional many-to-one association to Course
	@OneToMany(mappedBy="lieu")
	private List<Course> courses;

}