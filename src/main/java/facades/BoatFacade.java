package facades;

import dtos.BoatDTO;
import entities.Boat;
import entities.Harbour;
import entities.Owner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class BoatFacade
{
    private static EntityManagerFactory emf;
    private static BoatFacade instance;

    public BoatFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static BoatFacade getBoatFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BoatFacade();
        }
        return instance;
    }

// US-4
    public BoatDTO createBoat(BoatDTO boatDTO)
    {
        EntityManager em = emf.createEntityManager();
        Boat boat = new Boat(boatDTO.getBrand(), boatDTO.getMake(), boatDTO.getName(), boatDTO.getImage());

        TypedQuery<Harbour> query = em.createQuery("SELECT h FROM Harbour h WHERE h.id=:harbourId", Harbour.class);
        query.setParameter("harbourId", boatDTO.getHarbourID());
        Harbour harbour = query.getSingleResult();
        boat.setHarbour(harbour);
        try
        {
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();

        }finally
        {
            em.close();
        }
        return new BoatDTO(boat);
    }


    public BoatDTO connectBoatToHarbour(long boatID, long harbourId)
    {
        EntityManager em = emf.createEntityManager();
        Boat boat = em.find(Boat.class, boatID);
        Harbour harbour = em.find(Harbour.class, harbourId);
        boat.setHarbour(harbour);
        try
        {
            em.getTransaction().begin();
            em.merge(boat);
            em.getTransaction().commit();
        }finally
        {
            em.close();
        }
        return new BoatDTO(boat);
    }
}
