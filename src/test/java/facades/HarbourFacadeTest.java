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
    private static Owner ow1;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HarbourFacade.getHarbourFacade(emf);
    }

    @BeforeEach
    void setUp()
    {
        EntityManager em = emf.createEntityManager();
        h1 = new Harbour("harbourName1","testAddress1", 30);
        h2 = new Harbour("harbourName2","testAddress2", 60);

        b1 = new Boat("brand1", "make1", "testName1", "image1", h1);
        b2 = new Boat("brand2", "make2", "testName2", "image2", h1);
        b3 = new Boat("brand3", "make3", "testName3", "image3", h2);


        ow1 = new Owner("owner1","ownerAddress1","32432423");

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
}