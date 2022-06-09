package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Harbour.deleteAllRows", query = "DELETE from Harbour")
public class Harbour implements Serializable
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

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "harbour", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Boat> boats;

    public Harbour()
    {
    }

    public Harbour(String name, String address, int capacity)
    {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
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

    public int getCapacity()
    {
        return capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public List<Boat> getBoats()
    {
        return boats;
    }

    public void setBoats(List<Boat> boats)
    {
        this.boats = boats;
    }

    public void  addBoat(Boat boat){
        boats.add(boat);
    }
}
