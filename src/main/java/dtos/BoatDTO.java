package dtos;

import entities.Boat;
import entities.Owner;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

public class BoatDTO
{
    private long id;
    private String brand;
    private String make;
    private String name;
    private String image;
    private List<String> owners;
    private long harbourID;

    public BoatDTO(Boat boat){
        this.id = boat.getId();
        this.brand = boat.getBrand();
        this.make = boat.getMake();
        this.name = boat.getName();
        this.image = boat.getImage();
        this.owners = getOwners(boat.getOwners());
        this.harbourID = boat.getHarbour().getId();
    }

    public List<String> getOwners(List<Owner> owners){
        List<String> ownerList = new ArrayList<>();
        for (Owner o : owners)
        {
            ownerList.add(o.getName());
        }
        return ownerList;
    }

    public static List<BoatDTO> getDtos(List<Boat> boats) {
        List<BoatDTO> boatDTOS = new ArrayList();
        boats.forEach(boat -> boatDTOS.add(new BoatDTO(boat)));
        return boatDTOS;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public String getMake()
    {
        return make;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public List<String> getOwners()
    {
        return owners;
    }

    public void setOwners(List<String> owners)
    {
        this.owners = owners;
    }

    public long getHarbourID()
    {
        return harbourID;
    }

    public void setHarbourID(long harbourID)
    {
        this.harbourID = harbourID;
    }
}
