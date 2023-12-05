package course.pmu.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * The primary key class for the simple database table.
 * 
 */
@Data
@Embeddable
public class SimplePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false, unique=true, nullable=false)
	private int lieuId;

	@Column(insertable=false, updatable=false, unique=true, nullable=false)
	private int courseId;
	
	public SimplePK() {
	}
	
	public SimplePK(Integer lieuId, Integer courseId) {
		this.lieuId = lieuId;
		this.courseId = courseId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SimplePK)) {
			return false;
		}
		SimplePK castOther = (SimplePK)other;
		return 
			(this.lieuId == castOther.lieuId)
			&& (this.courseId == castOther.courseId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.lieuId;
		hash = hash * prime + this.courseId;
		
		return hash;
	}

	
}