package rest;

import javax.ws.rs.*;

@Path("boats")
public class BoatResource
{
    @GET
    @Produces("text/plain")
    public String hello()
    {
        return "Hello, World!";
    }
}