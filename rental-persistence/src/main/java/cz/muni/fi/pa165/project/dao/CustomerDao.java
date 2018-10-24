package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Customer;

import java.util.List;


/**
 * Interface of DAO for Customer entity providing basic CRUD operations
 * @author Juraj Vandor
 */
public interface CustomerDao {
    void create(Customer customer);
    Customer findById(Long id);
    List<Customer> findAll();
    void delete(Customer customer);
    void update(Customer customer);
}
