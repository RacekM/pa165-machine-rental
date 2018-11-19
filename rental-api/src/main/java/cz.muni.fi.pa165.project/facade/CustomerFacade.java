package cz.muni.fi.pa165.project.facade;

import cz.muni.fi.pa165.project.dto.CustomerCreateDTO;
import cz.muni.fi.pa165.project.dto.CustomerDTO;

import java.util.List;

/**
 * CustomerFacade Interface
 *
 * @author Martin Sisak, 445384
 */
public interface CustomerFacade {

    CustomerDTO getCustomerById(Long customerId);

    List<CustomerDTO> getAllCustomers();

    Long createCustomer(CustomerCreateDTO customerCreateDTO);

    void deleteCustomer(Long customerId);

    //List<CustomerDTO> getAllByCustomerType(CustomerType customerType)

}
