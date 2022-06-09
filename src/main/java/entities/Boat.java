package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Boat.deleteAllRows", query = "DELETE from Boat")
public class Boat implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

    @ManyToMany
    private List<Owner> owners;

    @ManyToOne
    private Harbour harbour;

    public Boat()
    {
    }

    public Boat(String brand, String make, String name, String image, Harbour harbour)
    {
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
        this.owners = new ArrayList<>();
        this.harbour = harbour;
    }

    public Boat(String brand, String make, String name, String image)
    {
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
        this.owners = new ArrayList<>();
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

    public List<Owner> getOwners()
    {
        return owners;
    }

    public void setOwners(List<Owner> owners)
    {
        this.owners = owners;
    }

    public Harbour getHarbour()
    {
        return harbour;
    }

    public void setHarbour(Harbour harbour)
    {
        this.harbour = harbour;
    }

    public  void addOwner(Owner owner){
        if (owner != null){
            this.owners.add(owner);
            owner.getBoats().add(this);
        }
    }
}
