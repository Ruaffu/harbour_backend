package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Owner.deleteAllRows", query = "DELETE from Owner")
public class Owner implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToMany(mappedBy = "owners", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Boat> boats;

    public Owner()
    {
    }

    public Owner(String name, String address, String phone)
    {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.boats = new ArrayList<>();
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public List<Boat> getBoats()
    {
        return boats;
    }

    public void setBoats(List<Boat> boats)
    {
        this.boats = boats;
    }

    public void addBoat(Boat boat){
        if (boat != null){
            this.boats.add(boat);
            boat.getOwners().add(this);
        }

    }
}
