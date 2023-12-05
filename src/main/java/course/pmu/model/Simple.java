package course.pmu.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;


/**
 * The persistent class for the simple database table.
 * 
 */
@Data
@Entity
@Table(name="simple", 
uniqueConstraints = { @UniqueConstraint(name = "simple_const", columnNames = { "courseId", "partant1", "partant2", "partant3" })
})
public class Simple implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "simpleId")
	private Integer id;

	@Column(name = "partant1", nullable=false)
	private Integer partant1;

	@Column(name = "partant2", nullable=false)
	private Integer partant2;

	@Column(name = "partant3", nullable=false)
	private Integer partant3;

	//one-directional many-to-one association to Course
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "courseId")
	private Course course;

}