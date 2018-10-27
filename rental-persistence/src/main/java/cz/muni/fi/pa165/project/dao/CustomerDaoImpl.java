package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of Customer Dao
 *
 * @author Martin Sisak (445384@mail.muni.cz)
 */

@Repository
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates new customer
     *
     * @param customer customer to be created
     */
    @Override
    public void create(Customer customer) {
        entityManager.persist(customer);
    }

    /**
     * Finds customer by his ID
     *
     * @param id id of the customer
     * @return customer
     */
    @Override
    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }

    /**
     * Finds all customers
     *
     * @return List of all customers
     */
    @Override
    public List<Customer> findAll() {
        return entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    /**
     * Deletes given customer
     *
     * @param customer customer to be deleted
     */
    @Override
    public void delete(Customer customer) {
        entityManager.remove(entityManager.merge(customer));
    }

    /**
     * Updates given customer
     *
     * @param customer customer to be updated
     */
    @Override
    public void update(Customer customer) {
        entityManager.merge(customer);
    }
}
