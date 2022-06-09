package facades;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
class OwnerFacadeTest
{
    private static EntityManagerFactory emf;
    private static OwnerFacade facade;
    private static Owner ow1,ow2,ow3;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = OwnerFacade.getOwnerFacade(emf);
    }

    @BeforeEach
    void setUp()
    {
        EntityManager em = emf.createEntityManager();
        ow1 = new Owner("owner1","ownerAddress1","32432423");
        ow2 = new Owner("owner2","ownerAddress2","24242347");
        ow3 = new Owner("owner3","ownerAddress3","45645663");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
            em.persist(ow1);
            em.persist(ow2);
            em.persist(ow3);
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
    public void getAllOwnersTest(){
        List<Owner> owners = facade.getAllOwners();
        int expected = 3;
        int actual = owners.size();

        assertEquals(expected, actual);
    }


}