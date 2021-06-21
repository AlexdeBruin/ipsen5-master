package nl.hsleiden.inf2b.groep4.environmentStatus;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.persistence.*;

@Entity
@Table(name = "status")
@NamedQueries(value = {
				@NamedQuery(name = "nl.hsleiden.inf2b.groep4.environmentStatus.StatusModel.findAll",
						query = "Select s from StatusModel s "),
		})
@Singleton
public class StatusModel {

	@Id
	@Column(name = "status_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int statusId;

	@Column(name = "status")
	private String status = "closed";

//	_____________________________
//	Constructors
//	_____________________________
	@Inject
	public StatusModel() {}

	public StatusModel(String status) {
		this.status = status;
	}

	public StatusModel(int statusId, String status) {
		this.statusId = statusId;
		this.status = status;
	}
//	_____________________________
//  getters and setters
// 	_____________________________

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
}
