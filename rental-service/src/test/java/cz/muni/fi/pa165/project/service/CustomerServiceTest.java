package cz.muni.fi.pa165.project.service;


import cz.muni.fi.pa165.project.dao.CustomerDao;
import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.enums.CustomerType;
import cz.muni.fi.pa165.project.service.configuration.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;


@ContextConfiguration(classes = ServiceConfiguration.class)
public class CustomerServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CustomerDao customerDao;

    @Inject
    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private Customer customerWithoutId;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createCustomers(){
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Customer");
        customer.setCustomerType(CustomerType.INDIVIDUAL);
        customerWithoutId = new Customer();
        customerWithoutId.setName("Customer WithoutId");
        customerWithoutId.setCustomerType(CustomerType.LEGAL_PERSON);
    }

    @Test
    public void getByIdTest(){
        when(customerDao.findById(customer.getId())).thenReturn(customer);
        Customer customer = customerService.findById(this.customer.getId());
        verify(customerDao).findById(this.customer.getId());
        assertEquals(this.customer, customer);
    }

    @Test
    public void getByNonExistingIdTest(){
        long id = -1;
        when(customerDao.findById(id)).thenReturn(null);
        assertNull(customerService.findById(id));
        verify(customerDao).findById(id);
    }

    @Test
    public void getAllCustomersTest(){
        List<Customer> customers = Collections.singletonList(customer);
        when(customerDao.findAll()).thenReturn(Collections.unmodifiableList(customers));
        List<Customer> customers_result = customerService.findAll();
        verify(customerDao, times(2)).findAll();
        assertEquals(customers, customers_result);

    }

    @Test
    public void getAllCustomersEmptyTest(){
        List<Customer> customers = Collections.EMPTY_LIST;
        when(customerDao.findAll()).thenReturn(Collections.unmodifiableList(customers));
        List<Customer> customers_result = customerService.findAll();
        verify(customerDao).findAll();
        assertEquals(customers, customers_result);
    }

    @Test
    public void createValidCustomerTest(){
        doAnswer(invocationOnMock -> {
            Customer customer = (Customer) invocationOnMock.getArguments()[0];
            customer.setId(10L);
            return null;
        }).when(customerDao).create(customerWithoutId);
        customerService.create(customerWithoutId);
        verify(customerDao).create(customerWithoutId);
        assertNotNull(customerWithoutId.getId());
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createInvalidCustomerTest(){
        customer.setCustomerType(null);
        doThrow(new DataIntegrityViolationException("")).when(customerDao).create(customer);
        customerService.create(customer);

    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullCustomerTest(){
        doThrow(new NullPointerException()).when(customerDao).create(null);
        customerService.create(null);

    }

    @Test
    public void updateValidCustomerTest(){
        doNothing().when(customerDao).update(customer);
        customerService.update(customer);
        verify(customerDao).update(customer);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void updateInvalidCustomerTest(){
        customer.setCustomerType(null);
        doThrow(new DataIntegrityViolationException("")).when(customerDao).update(customer);
        customerService.update(customer);
    }


    @Test(expectedExceptions = NullPointerException.class)
    public void updateNullCustomerTest(){
        doThrow(new NullPointerException()).when(customerDao).update(null);
        customerService.update(null);
    }

    @Test
    public void deleteExistingCustomerTest(){
        doNothing().when(customerDao).delete(customer);
        customerService.remove(customer);
        verify(customerDao).delete(customer);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void deleteNullCustomerTest(){
        doThrow(new InvalidDataAccessApiUsageException("")).when(customerDao).delete(null);
        customerService.remove(null);
    }


}