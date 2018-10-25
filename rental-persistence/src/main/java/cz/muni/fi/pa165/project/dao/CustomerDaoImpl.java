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

    @Override
    public void create(Customer customer) {
        entityManager.persist(customer);
    }

    @Override
    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }

    @Override
    public List<Customer> findAll() {
        return entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    @Override
    public void delete(Customer customer) {
        entityManager.remove(entityManager.merge(customer));
    }

    @Override
    public void update(Customer customer) {
        entityManager.merge(customer);
    }
}
