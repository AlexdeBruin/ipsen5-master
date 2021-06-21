package nl.hsleiden.inf2b.groep4.environmentStatus;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
public class StatusResource {

	private StatusService statusService;

	@Inject
	public StatusResource (StatusService statusService) {
		this.statusService = statusService;
	}

	@GET
	@UnitOfWork
	@RolesAllowed("GROUP")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStatus() {
		return statusService.getStatus();
	}

	@POST
	@Path("change")
	@UnitOfWork
	@RolesAllowed("ADMIN")
	public void changeStatus(String status) {
		statusService.setStatus(status);
	}


}
