package nl.hsleiden.inf2b.groep4.cost_card;

import com.google.inject.Singleton;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;
import java.util.List;

@Singleton
public class CostCardDAO extends AbstractDAO<CostCard> {

    @Inject
    public CostCardDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    /**
     * testen of de database de gegevens op kan halen
     */
    public List<CostCard> getAllItems() {

        return list(namedQuery("nl.hsleiden.inf2b.groep4.Interpreter.CostCardModel.findAll"));
    }


    public CostCard getCostCardById(int costCardId) {
        return get(costCardId);
    }


    public void saveCostCard(CostCard costCard) {
        Session session = currentSession().getSession();
        session.save(costCard);
        session.getTransaction().commit();

    }
}
