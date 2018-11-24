package cz.muni.fi.pa165.project.service.facade;


import cz.muni.fi.pa165.project.dto.CustomerCreateDTO;
import cz.muni.fi.pa165.project.dto.CustomerDTO;
import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.enums.CustomerType;
import cz.muni.fi.pa165.project.facade.CustomerFacade;
import cz.muni.fi.pa165.project.service.BeanMappingService;
import cz.muni.fi.pa165.project.service.CustomerService;
import cz.muni.fi.pa165.project.service.configuration.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Test for the {@link CustomerFacade}
 *
 * @author Martin Sisak, 445384
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CustomerFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CustomerService customerService;

    @Spy
    @Inject
    private BeanMappingService beanMappingService;

    @InjectMocks
    private CustomerFacade customerFacade = new CustomerFacadeImpl();

    private Customer customer1;
    private Customer customer2;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createCustomers(){
        customer1 = new Customer();
        customer1.setName("Customer1");
        customer1.setCustomerType(CustomerType.LEGAL_PERSON);
        customer2 = new Customer();
        customer2.setName("Customer2");
        customer2.setCustomerType(CustomerType.INDIVIDUAL);
    }

    @Test
    public void getCustomerByIdTest(){
        when(customerService.findById(customer1.getId())).thenReturn(customer1);
        CustomerDTO customerDTO = customerFacade.getCustomerById(customer1.getId());
        verify(customerService, times(2)).findById(customer1.getId());
        assertEquals(customer1, beanMappingService.mapTo(customerDTO, Customer.class));
    }

    @Test
    public void getAllCustomersTest(){
        List<Customer> customersList = Arrays.asList(customer1, customer2);
        when(customerService.findAll()).thenReturn(customersList);
        List<CustomerDTO> customerDTOList = customerFacade.getAllCustomers();
        verify(customerService).findAll();
        List<Customer> customersResultList = beanMappingService.mapTo(customerDTOList, Customer.class);
        assertEquals(customersList, customersResultList);
    }

    @Test
    public void createCustomerTest(){
        CustomerCreateDTO customerCreateDTO = new CustomerCreateDTO();
        customerCreateDTO.setName("new customer");
        customerCreateDTO.setCustomerType(CustomerType.INDIVIDUAL);
        customerFacade.createCustomer(customerCreateDTO);
        verify(customerService).create(any(Customer.class));
    }

    @Test
    public void deleteCustomerTest(){
        when(customerService.findById(customer1.getId())).thenReturn(customer1);
        customerFacade.deleteCustomer(customer1.getId());
        verify(customerService).findById(customer1.getId());
        verify(customerService).remove(customer1);
    }

    @Test
    public void updateCustomerTest(){
        CustomerCreateDTO customerCreateDTO = new CustomerCreateDTO();
        customerCreateDTO.setName("Customer");
        customerCreateDTO.setCustomerType(CustomerType.LEGAL_PERSON);
        customerFacade.updateCustomer(customerCreateDTO);
        verify(customerService).update(any(Customer.class));
    }

    @Test
    public void getAllByCustomerTypeTest(){
        List<Customer> customers = Arrays.asList(customer1);
        when(customerService.getAllByCustomerType(CustomerType.LEGAL_PERSON)).thenReturn(customers);
        List<CustomerDTO> customerDTOS = customerFacade.getAllByCustomerType(CustomerType.LEGAL_PERSON);
        verify(customerService).getAllByCustomerType(CustomerType.LEGAL_PERSON);
        List<Customer> customersResult = beanMappingService.mapTo(customerDTOS, Customer.class);
        assertEquals(customers, customersResult);
    }

}
