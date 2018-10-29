package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Customer;

import java.util.List;


/**
 * Interface of DAO for Customer entity providing basic CRUD operations
 * @author Juraj Vandor
 */
public interface CustomerDao {

    /**
     * Persists customer in data storage
     *
     * @param customer customer to be persisted
     */
    void create(Customer customer);

    /**
     * Finds customer from data storage by id
     *
     * @param id customers id
     * @return customer entity with given id, null if no such customer exists
     */
    Customer findById(Long id);

    /**
     * Finds all customers
     *
     * @return list of all customers in data storage
     */
    List<Customer> findAll();

    /**
     * Removes customer from data storage
     *
     * @param customer customer to be removed
     */
    void delete(Customer customer);

    /**
     * Updates customer in data storage
     *
     * @param customer updated customer entity
     */
    void update(Customer customer);
}
