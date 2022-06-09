package dtos;

import entities.Boat;
import entities.Owner;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class OwnerDTO
{
    private long id;
    private String name;
    private String address;
    private String phone;
    private List<String> boats;

    public OwnerDTO(Owner owner){
        this.id = owner.getId();
        this.name = owner.getName();
        this.address = owner.getAddress();
        this.phone = owner.getPhone();
        this.boats = getBoats(owner.getBoats());
    }

    public List<String> getBoats(List<Boat> boats){
        List<String> boatList = new ArrayList<>();
        for (Boat b : boats)
        {
            boatList.add("Brand: "+b.getBrand()+" Make: "+ b.getMake()+" Name: "+b.getName());

        }
        return boatList;
    }

    public static List<OwnerDTO> getDtos(List<Owner> owners) {
        List<OwnerDTO> ownerDTOS = new ArrayList();
        owners.forEach(owner -> ownerDTOS.add(new OwnerDTO(owner)));
        return ownerDTOS;
    }
}
