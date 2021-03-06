package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDTO;
import entities.Boat;
import entities.Harbour;
import entities.Owner;
import facades.BoatFacade;
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
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

class BoatResourceTest
{
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static BoatFacade facade;
    private static Boat b1,b2,b3,b4;
    private static Harbour h1,h2;
    private static Owner ow1,ow2;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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
        b4 = new Boat("sdfsf", "Predator","60 evo","image");


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
            em.persist(b4);
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
    public void testCreateBoat() throws Exception {
        Boat boat = new Boat("testBrand", "MTest","M2","imagetest");
        boat.setHarbour(h1);

        given()
                .contentType("application/json")
                .body(new BoatDTO(boat))
                .when()
                .post("/boats/create")
                .then()
                .statusCode(200)
                .body("brand", equalTo("testBrand"));
    }

    @Test
    public void testConnectHarbour() throws Exception{
        given()
                .contentType("application/json")
                .when()
                .put("/boats/{bid}/connect/{hid}", b4.getId(), h1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("harbourID", equalTo(Integer.valueOf(String.valueOf(h1.getId()))));

    }

    @Test
    public void testUpdateBoat() throws Exception{
        Boat b5 = new Boat("Malibu Boats", "M-series","yak","image",h1);
        ow1.addBoat(b5);
        BoatDTO boatDTO = new BoatDTO(b5);

        given()
                .contentType("application/json")
                .and()
                .body(GSON.toJson(boatDTO))
                .when()
                .put("/boats/{bid}/update", b3.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("yak"))
                .body("harbourID", equalTo(Integer.valueOf(String.valueOf(h1.getId()))));

    }

    @Test
    public void testDeleteBoat() throws Exception{
        given()
                .contentType("application/json")
                .and()
                .when()
                .delete("/boats/{bid}/delete", b3.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("60 evo"));
    }
}