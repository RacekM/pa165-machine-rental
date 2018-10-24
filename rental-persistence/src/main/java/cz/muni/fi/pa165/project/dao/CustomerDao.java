package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Customer;

import java.util.List;

public interface CustomerDao {
    public void create(Customer customer);
    public Customer findById(Long id);
    public List<Customer> findAll();
    public void delete(Customer customer);
    public void update(Customer customer);
}
