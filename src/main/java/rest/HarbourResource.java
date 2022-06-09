package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDTO;
import entities.Boat;
import facades.HarbourFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("harbour")
public class HarbourResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final HarbourFacade FACADE = HarbourFacade.getHarbourFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("text/plain")
    public String hello()
    {
        return "Hello, World!";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public  String getBoatsByHarbourID(@PathParam("id") long id){
        List<Boat> boats = FACADE.getBoatsByHarbourID(id);
        List<BoatDTO> boatDTOS = BoatDTO.getDtos(boats);

        return GSON.toJson(boatDTOS);
    }
}