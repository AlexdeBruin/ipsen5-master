package nl.hsleiden.inf2b.groep4.progress;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("progress")
@Produces(MediaType.APPLICATION_JSON)
public class ProgressResource {
	private ProgressService progressService;

	@Inject
	public ProgressResource(ProgressService progressService) {
		this.progressService = progressService;
	}

	@GET
	@UnitOfWork
	@RolesAllowed("GROUP")
	@Path("/amountsolved/{accountId}")
	public Response getProgress(@PathParam("accountId") int accountId){
		return progressService.getAmountSolvedPuzzle(accountId);
	}

	@DELETE
	@Path("/deletepuzzle/{accountId}")
	@RolesAllowed("GROUP")
	@UnitOfWork
	public Response deletePuzzle(@PathParam("accountId") int accountId){
		return this.progressService.deletePuzzle(accountId);
	}
}
