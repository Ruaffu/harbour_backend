package facades;

import entities.RenameMe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

//Uncomment the line below, to temporarily disable this test
//@Disabled
class HarbourFacadeTest
{
    private static EntityManagerFactory emf;
    private static HarbourFacade facade;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HarbourFacade.getHarbourFacade(emf);
    }

    @BeforeEach
    void setUp()
    {
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.createNamedQuery("RenameMe.deleteAllRows").executeUpdate();
//            em.persist(new RenameMe("Some txt", "More text"));
//            em.persist(new RenameMe("aaa", "bbb"));
//
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
    }

    @AfterEach
    void tearDown()
    {
    }
}