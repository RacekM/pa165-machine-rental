package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.enums.CustomerType;

import java.util.List;

/**
 * Service Interface to the entity {@link Customer}
 *
 * @author Martin Sisak, 445384
 */
public interface CustomerService {
    /**
     *
     * @param customerId Identifier for customer
     * @return Found customer if data storage contains it, null otherwise
     */
    Customer findById(Long customerId);

    /**
     * Finds all customers
     * @return List of all customers from data storage
     */
    List<Customer> findAll();

    /**
     * Creates new customer
     * @param customer Customer to create
     */
    void create(Customer customer);

    /**
     * Deletes customer
     * @param customer Customer to delete
     */
    void remove(Customer customer);

    /**
     * Updates customer
     * @param customer Customer to update
     */
    void update(Customer customer);

    /**
     * Finds all customers with specified CustomerType
     * @param customerType Type of customer
     * @return List of customers with specified CustomerType
     */
    List<Customer> getAllByCustomerType(CustomerType customerType);



}
