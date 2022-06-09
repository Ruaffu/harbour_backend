package facades;

import entities.Boat;
import entities.Owner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class OwnerFacade
{
    private static EntityManagerFactory emf;
    private static OwnerFacade instance;

    public OwnerFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static OwnerFacade getOwnerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new OwnerFacade();
        }
        return instance;
    }

// US-1
    public List<Owner> getAllOwners()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o ", Owner.class);
            List<Owner> result = query.getResultList();
            return result;
        }finally
        {
            em.close();
        }
    }
}
