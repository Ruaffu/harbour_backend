package utils;


import entities.*;
import facades.UserFacade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "test123");
    User admin = new User("admin", "test123");
    User both = new User("user_admin", "test123");

    Owner owner = new Owner("Bob Fellows","Millionaire Street","2423423");
    Owner owner2 = new Owner("Bo momo","Yup Street","5559999");
    Harbour harbour = new Harbour("Victoria Harbour","Admiralty, Hong Kong",50);
    Harbour harbour2 = new Harbour("VNeko Harbour","Antarctica",10);
    Boat boat1 = new Boat("Malibu Boats", "M-series","M240","image",harbour);
    Boat boat2 = new Boat("Malibu Boats", "Responce","TXi MO","image",harbour);
    Boat boat3 = new Boat("Sunseeker", "Predator","60 evo","image",harbour2);


    harbour.addBoat(boat1);
    harbour.addBoat(boat2);
    harbour2.addBoat(boat3);

    owner.addBoat(boat1);
    owner.addBoat(boat2);
    owner2.addBoat(boat3);


    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
    {
      throw new UnsupportedOperationException("You have not changed the passwords");
    }

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
//    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);
    em.persist(userRole);
    em.persist(adminRole);
//    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.persist(boat1);
    em.persist(boat2);
    em.persist(boat3);
    em.persist(harbour);
    em.persist(harbour2);
    em.persist(owner);
    em.persist(owner2);
    em.getTransaction().commit();
    UserFacade.getUserFacade(emf).registerNewUser(user);
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test123"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
   
  }

}
