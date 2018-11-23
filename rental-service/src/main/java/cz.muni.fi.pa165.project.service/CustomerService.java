package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.entity.Customer;

import java.util.List;

/**
 * Service Interface to the entity {@link Customer}
 *
 * @author Martin Sisak, 445384
 */
public interface CustomerService {

    Customer findById(Long customerId);

    List<Customer> findAll();

    void create(Customer customer);

    void remove(Customer customer);

    void update(Customer customer);

}
