package nl.hsleiden.inf2b.groep4.InitializePuzzleAssets;

import com.google.inject.Singleton;
import io.dropwizard.hibernate.UnitOfWork;

import com.google.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by VincentSpijkers on 23/05/2018.
 */

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path("/insertBlocks")
public class InitializeResource {

    private InitializeService service;

    @Inject
    public InitializeResource(InitializeService service) {
        this.service = service;
    }

    @UnitOfWork
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void insertData(){
        this.service.insertData();
    }

}
