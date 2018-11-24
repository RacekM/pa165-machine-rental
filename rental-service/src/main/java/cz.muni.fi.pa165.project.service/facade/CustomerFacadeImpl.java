package cz.muni.fi.pa165.project.service.facade;

import cz.muni.fi.pa165.project.dto.CustomerCreateDTO;
import cz.muni.fi.pa165.project.dto.CustomerDTO;
import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.enums.CustomerType;
import cz.muni.fi.pa165.project.facade.CustomerFacade;
import cz.muni.fi.pa165.project.service.BeanMappingService;
import cz.muni.fi.pa165.project.service.CustomerService;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of {@link CustomerFacade}
 *
 * @author Martin Sisak, 445384
 */
public class CustomerFacadeImpl implements CustomerFacade {

    @Inject
    private CustomerService customerService;

    @Inject
    private BeanMappingService beanMappingService;


    @Override
    public CustomerDTO getCustomerById(Long customerId) {
        Customer customer = customerService.findById(customerId);
        return (customer == null) ? null : beanMappingService.mapTo(customer, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return beanMappingService.mapTo(customerService.findAll(), CustomerDTO.class);
    }

    @Override
    public Long createCustomer(CustomerCreateDTO customerCreateDTO) {
        Customer customer = beanMappingService.mapTo(customerCreateDTO, Customer.class);
        customerService.create(customer);
        return customer.getId();
    }

    @Override
    public void deleteCustomer(Long customerId) {
        Customer customer = customerService.findById(customerId);
        if (customer != null){
            customerService.remove(customer);
        }
    }

    @Override
    public void updateCustomer(CustomerCreateDTO customerCreateDTO){
        Customer customer = beanMappingService.mapTo(customerCreateDTO, Customer.class);
        customerService.update(customer);
    }

    @Override
    public List<CustomerDTO> getAllByCustomerType(CustomerType customerType) {
        List<Customer> customers = customerService.getAllByCustomerType(customerType);
        return beanMappingService.mapTo(customers, CustomerDTO.class);
    }
}
