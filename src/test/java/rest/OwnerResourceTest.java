package rest;

import entities.Boat;
import entities.Harbour;
import entities.Owner;
import facades.HarbourFacade;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;

class OwnerResourceTest
{
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static HarbourFacade facade;
    private static Boat b1,b2,b3;
    private static Harbour h1,h2;
    private static Owner ow1,ow2;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
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

    @Test
    public void testGetAllOwners() throws Exception {
        given()
                .contentType("application/json")
                .get("/owner/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", hasItems("Bob Fellows", "Bo momo"));
    }
}