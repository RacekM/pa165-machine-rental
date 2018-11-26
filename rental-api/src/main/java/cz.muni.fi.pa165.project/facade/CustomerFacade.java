package cz.muni.fi.pa165.project.facade;

import cz.muni.fi.pa165.project.dto.CustomerCreateDTO;
import cz.muni.fi.pa165.project.dto.CustomerDTO;
import cz.muni.fi.pa165.project.enums.CustomerType;

import java.util.List;

/**
 * CustomerFacade Interface
 *
 * @author Martin Sisak, 445384
 */
public interface CustomerFacade {

    /**
     * Finds customer by his id
     *
     * @param customerId Identifier for customer
     * @return Finds customer if data storage contains it, null otherwise
     */
    CustomerDTO getCustomerById(Long customerId);

    /**
     * Finds all customers
     *
     * @return List of all customers
     */
    List<CustomerDTO> getAllCustomers();

    /**
     * Creates new customer
     *
     * @param customerCreateDTO customer to be created
     */
    Long createCustomer(CustomerCreateDTO customerCreateDTO);

    /**
     * Deletes customer
     *
     * @param customerId ID of customer to be deleted
     */
    void deleteCustomer(Long customerId);

    /**
     * Updates customer
     *
     * @param customerCreateDTO customer for update
     */
    void updateCustomer(CustomerCreateDTO customerCreateDTO);

    /**
     * Gets all customers with specified CustomerType
     *
     * @param customerType Type of customer
     * @return List of all customers with specified CustomerType
     */
    List<CustomerDTO> getAllByCustomerType(CustomerType customerType);

}
