package facades;

import dtos.BoatDTO;
import dtos.OwnerDTO;
import entities.Boat;
import entities.Harbour;
import entities.Owner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class BoatFacade
{
    private static EntityManagerFactory emf;
    private static BoatFacade instance;

    public BoatFacade()
    {
    }

    /**
     * @param _emf
     * @return the instance of this facade.
     */
    public static BoatFacade getBoatFacade(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
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

        } finally
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
        } finally
        {
            em.close();
        }
        return new BoatDTO(boat);
    }

    //US-6
    public BoatDTO updateBoat(long boatId, BoatDTO boatDTO)
    {
        EntityManager em = emf.createEntityManager();
        Boat boat = em.find(Boat.class, boatId);
        boat.setBrand(boatDTO.getBrand());
        boat.setMake(boatDTO.getMake());
        boat.setName(boatDTO.getName());
        boat.setImage(boatDTO.getImage());

        //clears owners
        boat.getOwners().clear();
        System.out.println(boatDTO.getOwners());

        for (String owner : boatDTO.getOwners())
        {
            Owner ownerFound = em.createQuery("SELECT o FROM Owner o WHERE o.name = :owner", Owner.class).setParameter("owner", owner).getSingleResult();

            boat.addOwner(ownerFound);

        }
        System.out.println(boat.getOwners());
        try
        {
            TypedQuery<Harbour> query = em.createQuery("SELECT h FROM Harbour h WHERE h.id =:harbourId", Harbour.class);
            query.setParameter("harbourId", boatDTO.getHarbourID());
            Harbour harbour = query.getSingleResult();
            boat.setHarbour(harbour);

            em.getTransaction().begin();
            em.merge(boat);
            em.getTransaction().commit();

        } finally
        {
            em.close();
        }
        return new BoatDTO(boat);
    }

    public BoatDTO deleteBoat(long boatId)
    {
        EntityManager em = emf.createEntityManager();
        Boat boat = em.find(Boat.class, boatId);
        try
        {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM BOAT_OWNER WHERE boats_id = ?").setParameter(1, boat.getId()).executeUpdate();
            em.remove(boat);
            em.getTransaction().commit();
        }finally
        {
            em.close();
        }
        return new BoatDTO(boat);
    }

    public List<Boat> getAllBoats()
    {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b", Boat.class);
            List<Boat> result = query.getResultList();
            return result;
        } finally {
            em.close();
        }
    }
}
