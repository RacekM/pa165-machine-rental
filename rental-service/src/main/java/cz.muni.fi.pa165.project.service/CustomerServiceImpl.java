package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.dao.CustomerDao;
import cz.muni.fi.pa165.project.entity.Customer;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of {@link CustomerService}
 *
 * @author Martin Sisak, 445384
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService{

    @Inject
    private CustomerDao customerDao;

    @Override
    public Customer findById(Long customerId) { return customerDao.findById(customerId);}

    @Override
    public List<Customer> findAll() { return customerDao.findAll(); }

    @Override
    public void create(Customer customer) { customerDao.create(customer);}

    @Override
    public void remove(Customer customer) { customerDao.delete(customer);}

    @Override
    public void update(Customer customer) { customerDao.update(customer); }
}
