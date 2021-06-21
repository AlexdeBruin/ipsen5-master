package nl.hsleiden.inf2b.groep4.cost_card;


import com.google.inject.Singleton;
import io.dropwizard.hibernate.UnitOfWork;


import com.google.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@Path("/CostCard")
@Produces(MediaType.APPLICATION_JSON)
public class CostCardResource {

    private CostCardDAO costCardDAO;

    @Inject
    public CostCardResource(CostCardDAO costCardDAO) {
        this.costCardDAO = costCardDAO;
    }


    @GET
    @UnitOfWork
    public List<CostCard> findAll() {
        return costCardDAO.getAllItems();
    }

    @GET
    @Path("/CostCardId")
    @UnitOfWork
    public CostCard findById(@QueryParam("id") int costCardId) {
        return costCardDAO.getCostCardById(costCardId);
    }

    @POST
    @Path("/saveCostCard")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public void createCostCard(CostCard costCard) {
        this.costCardDAO.saveCostCard(costCard);
    }

}
