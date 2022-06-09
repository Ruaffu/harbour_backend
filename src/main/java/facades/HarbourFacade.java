package facades;

import entities.Boat;
import entities.Owner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

public class HarbourFacade
{
    private static EntityManagerFactory emf;
    private static HarbourFacade instance;

    public HarbourFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static HarbourFacade getHarbourFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HarbourFacade();
        }
        return instance;
    }

// US-2
    public List<Boat> getBoatsByHarbourID(long harbourId)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b where b.harbour.id =:harbourId", Boat.class);
            query.setParameter("harbourId", harbourId);
            List<Boat> result = query.getResultList();
            return result;
        }finally
        {
            em.close();
        }
    }

    public List<Owner> getOwnerByBoatId(long id)
    {
        EntityManager em = emf.createEntityManager();

        Boat boat = em.find(Boat.class, id);
        return boat.getOwners();
    }
}
