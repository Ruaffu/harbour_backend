package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDTO;
import entities.Boat;
import facades.BoatFacade;
import facades.HarbourFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("boats")
public class BoatResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final BoatFacade FACADE = BoatFacade.getBoatFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @GET
    @Produces("text/plain")
    public String hello()
    {
        return "Hello, World!";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("create")
    public String createBoat(String data){
        BoatDTO boatDTO = GSON.fromJson(data, BoatDTO.class);
        BoatDTO newBoatDTO = FACADE.createBoat(boatDTO);
        return GSON.toJson(newBoatDTO);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{bid}/connect/{hid}")
    public String connectHarbour(@PathParam("bid") long boatId, @PathParam("hid") long harbourId){
        BoatDTO boatDTO = FACADE.connectBoatToHarbour(boatId, harbourId);
        return GSON.toJson(boatDTO);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{bid}/update")
    public String updateBoat(@PathParam("bid") long boatId,String data){
        System.out.println(data);
        BoatDTO boatDTO = GSON.fromJson(data, BoatDTO.class);
        System.out.println(boatDTO);
        BoatDTO updatedBoatDTO = FACADE.updateBoat(boatId,boatDTO);
        return GSON.toJson(updatedBoatDTO);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{bid}/delete")
    public String deleteBoat(@PathParam("bid") long boatId){
        BoatDTO boatDTO = FACADE.deleteBoat(boatId);
        return GSON.toJson(boatDTO);
    }
}