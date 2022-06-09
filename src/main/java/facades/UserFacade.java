package facades;

import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public User registerNewUser(User user){
        EntityManager em = emf.createEntityManager();
        Role role = new Role("user");
        user.addRole(role);

        try
        {
            if (em.find(User.class, user.getUserName()) == null){
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            } else throw new Exception("User already exists :(");
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            em.close();
        }
        return user;
    }

    //ToDo: register admin, possibly change name and password


}
