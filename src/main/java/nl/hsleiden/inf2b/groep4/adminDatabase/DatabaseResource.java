package nl.hsleiden.inf2b.groep4.adminDatabase;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/backup")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class DatabaseResource {

    private DatabaseService databaseService;

    @Inject
    public DatabaseResource(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }


    @POST
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public void createBackUp() {
        databaseService.createBackUp();

    }

    @POST
    @Path("restore")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public void restoreBackUp() {
        databaseService.restoreBackUp();
    }

    @POST
    @Path("wipeAll")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public void wipeAll() {
       databaseService.wipeAll();
    }

    @POST
    @RolesAllowed("ADMIN")
    @Path("exitsystem")
    public void stopApplication() throws InterruptedException {
     //   backUpService.exitSystem();
    }

    @POST
    @RolesAllowed("ADMIN")
    @Path("grades")
    public void exportGrades() {
        databaseService.exportGrades();
    }

//    @POST
//    @Path("")

}
