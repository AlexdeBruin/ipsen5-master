package nl.hsleiden.inf2b.groep4.environmentStatus;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class StatusService {


	private StatusDAO statusDAO;

	@Inject
	public StatusService(StatusDAO statusDAO) {
		this.statusDAO = statusDAO;
	}

	public void setStatus(String status) {
		StatusModel statusModel = new StatusModel(status);
		this.statusDAO.setStatus(statusModel);
	}

	public String getStatus() {
		StatusModel model = statusDAO.getStatus();
		return model.getStatus();
	}
}
