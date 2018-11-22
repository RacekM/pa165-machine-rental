package cz.muni.fi.pa165.project.service.facade;

import cz.muni.fi.pa165.project.dto.CustomerCreateDTO;
import cz.muni.fi.pa165.project.dto.CustomerDTO;
import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.facade.CustomerFacade;
import cz.muni.fi.pa165.project.service.BeanMappingService;
import cz.muni.fi.pa165.project.service.CustomerService;

import javax.inject.Inject;
import java.util.List;

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
        Customer customer = new Customer();
        customer.setName(customerCreateDTO.getName());
        customer.setCustomerType(customerCreateDTO.getCustomerType());
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
}
