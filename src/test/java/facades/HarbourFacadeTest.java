package facades;

import entities.Boat;
import entities.Harbour;
import entities.Owner;
import entities.RenameMe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Uncomment the line below, to temporarily disable this test
//@Disabled
class HarbourFacadeTest
{
    private static EntityManagerFactory emf;
    private static HarbourFacade facade;
    private static Boat b1,b2,b3;
    private static Harbour h1,h2;
    private static Owner ow1,ow2;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HarbourFacade.getHarbourFacade(emf);
    }

    @BeforeEach
    void setUp()
    {
        EntityManager em = emf.createEntityManager();
        ow1 = new Owner("Bob Fellows","Millionaire Street","2423423");
        ow2 = new Owner("Bo momo","Yup Street","5559999");
        h1 = new Harbour("Victoria Harbour","Admiralty, Hong Kong",50);
        h2  = new Harbour("VNeko Harbour","Antarctica",10);
        b1 = new Boat("Malibu Boats", "M-series","M240","image",h1);
        b2 = new Boat("Malibu Boats", "Responce","TXi MO","image",h1);
        b3 = new Boat("Sunseeker", "Predator","60 evo","image",h2);


        h1.addBoat(b1);
        h1.addBoat(b2);
        h2.addBoat(b3);

        ow1.addBoat(b1);
        ow1.addBoat(b2);
        ow2.addBoat(b3);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
            em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
            em.createNamedQuery("Harbour.deleteAllRows").executeUpdate();
            em.persist(b1);
            em.persist(b2);
            em.persist(b3);
            em.persist(h1);
            em.persist(h2);
            em.persist(ow1);
            em.persist(ow2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown()
    {
    }

    //US-2
    @Test
    public void getBoatsByHarbourTest(){
        List<Boat> boats = facade.getBoatsByHarbourID(h1.getId());
        int expected = 2;
        int actual = boats.size();

        assertEquals(expected, actual);
    }

    //US-3
    @Test
    public void getOwnersByBoat(){
        List<Owner> owners = facade.getOwnerByBoatId(b1.getId());
        int expected = 1;
        int actual = owners.size();

        assertEquals(expected, actual);
    }
}