package cz.muni.fi.pa165.project;


import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.enums.CustomerType;

import javax.persistence.*;

public class Main {

    @PersistenceUnit
    private EntityManagerFactory emf;

    public static void main(String[] args) {
        new Main().test();
    }
    public void test(){
        //should be in constructor
        emf = Persistence.createEntityManagerFactory("default");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction t = entityManager.getTransaction();
        t.begin();
        Customer c = new Customer("jozo", CustomerType.INDIVIDUAL);
        entityManager.persist(c);
        t.commit();
        entityManager.close();
        entityManager = emf.createEntityManager();
        t = entityManager.getTransaction();
        t.begin();
        System.out.append(entityManager.find(Customer.class, c.getId()).toString());
        t.commit();
        entityManager.close();
    }

}
