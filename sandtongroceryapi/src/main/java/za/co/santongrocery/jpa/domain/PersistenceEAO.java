package za.co.santongrocery.jpa.domain;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PersistenceEAO {
    private static final Logger LOGGER = Logger.getLogger(PersistenceEAO.class);
    public static final String HIBERNATE_JTA = "Hibernate_JTA";
    @PersistenceContext(unitName = HIBERNATE_JTA)
    private EntityManager entityManager ;

    protected EntityManager getEntityManager() {
        if(null==entityManager){
            LOGGER.info("entity manager is null");
        }
        return entityManager;
    }
}
