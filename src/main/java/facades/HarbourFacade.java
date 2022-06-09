package facades;

import javax.persistence.EntityManagerFactory;

public class HarbourFacade
{
    private static EntityManagerFactory emf;
    private static HarbourFacade instance;

    public HarbourFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static HarbourFacade getHarbourFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HarbourFacade();
        }
        return instance;
    }


}
