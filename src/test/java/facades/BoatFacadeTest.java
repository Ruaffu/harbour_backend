package facades;

import dtos.BoatDTO;
import dtos.OwnerDTO;
import entities.Boat;
import entities.Harbour;
import entities.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
class BoatFacadeTest
{
    private static EntityManagerFactory emf;
    private static BoatFacade facade;
    private static HarbourFacade harbourFacade;
    private static Boat b1,b2,b3,b5;
    private static Harbour h1,h2;
    private static Owner ow1,ow2;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = BoatFacade.getBoatFacade(emf);
        harbourFacade = HarbourFacade.getHarbourFacade(emf);
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
        b5 = new Boat("momoney", "Predator2","evo","image15");


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
            em.persist(b5);
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

    //US-4
    @Test
    public void createBoatTest(){
        Boat b4 = new Boat("testBrand", "testMake","testName","testImage");
        b4.setHarbour(h2);
        BoatDTO newBoat = new BoatDTO(b4);
        BoatDTO newBoatDTO = facade.createBoat(newBoat);
        List<Boat> boats = harbourFacade.getBoatsByHarbourID(h2.getId());

        int expected = 2;
        int actual = boats.size();

        assertEquals(expected, actual);
    }

    //US-5
    @Test
    public void connectBoatWithHarbourTest(){
        facade.connectBoatToHarbour(b5.getId(), h2.getId());

        List<Boat> boats = harbourFacade.getBoatsByHarbourID(h2.getId());

        int expected = 2;
        int actual = boats.size();

        assertEquals(expected, actual);

    }

    //US-6
    @Test
    public void updateInformationTest(){

        b1.setBrand("new Brand");
        b1.setHarbour(h2);
        b1.addOwner(ow2);

        BoatDTO updatedBoat = new BoatDTO(b1);
        facade.updateBoat(b1.getId(),updatedBoat);

        assertEquals("new Brand", updatedBoat.getBrand());
        assertEquals(h2.getId(), updatedBoat.getHarbourID());

        OwnerDTO ownerDTO = new OwnerDTO(ow2);

    }

    //US-7
    @Test
    public void deleteBoatTest(){
        facade.deleteBoat(b3.getId());
        List<Boat> boats = facade.getAllBoats();
        int expected = 3;
        int actual = boats.size();

        assertEquals(expected, actual);

    }

}