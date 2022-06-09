package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.OwnerDTO;
import entities.Owner;
import facades.HarbourFacade;
import facades.OwnerFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("owner")
public class OwnerResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final OwnerFacade FACADE = OwnerFacade.getOwnerFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("text/plain")
    public String hello()
    {
        return "Hello, World!";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allOwners() {
        List<Owner> owners = FACADE.getAllOwners();
        List<OwnerDTO> ownerDTOS = OwnerDTO.getDtos(owners);

        return GSON.toJson(ownerDTOS);

    }
}