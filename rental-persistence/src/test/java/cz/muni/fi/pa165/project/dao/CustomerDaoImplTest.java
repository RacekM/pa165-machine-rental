package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.PersistenceApplicationContext;
import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.enums.CustomerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CustomerDaoImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public CustomerDao customerDao;

//    private Customer customerAdam;
//    private Customer customerMatus;

    @BeforeClass
    public void setup() {
        //customerAdam = new Customer("Adam", CustomerType.INDIVIDUAL);
        //customerMatus = new Customer("Matus", CustomerType.LEGAL_PERSON);
    }

    @Test
    public void createCustomerTest() {
        Customer customerAdam = new Customer("Adam", CustomerType.INDIVIDUAL);
        customerDao.create(customerAdam);

        Customer customer = entityManager.find(Customer.class, customerAdam.getId());
        Assert.assertNotNull(customer);
        Assert.assertEquals(customer, customerAdam);
    }

//    @Test(expectedExceptions = {ConstraintViolationException.class})
//    public void createCustomerWithNullAttribute() {
//        Customer customer = new Customer(null, CustomerType.LEGAL_PERSON);
//        customerDao.create(customer);
//    }

    @Test
    public void findCustomerByIdTest() {
        Customer customerAdam = new Customer("Adam", CustomerType.INDIVIDUAL);
        entityManager.persist(customerAdam);

        Customer customer = customerDao.findById(customerAdam.getId());
        Assert.assertNotNull(customer);
        Assert.assertEquals(customer, customerAdam);
    }

    @Test
    public void findNonExistingCustomerTest() {
        Customer customerAdam = new Customer("Adam", CustomerType.INDIVIDUAL);
        Customer customer = customerDao.findById(customerAdam.getId());
        Assert.assertNull(customer);
    }

    @Test
    public void findAllCustomersTest() {
        Customer customerAdam = new Customer("Adam", CustomerType.INDIVIDUAL);
        Customer customerMatus = new Customer("Matus", CustomerType.LEGAL_PERSON);
        entityManager.persist(customerAdam);
        entityManager.persist(customerMatus);

        List<Customer> customers = customerDao.findAll();
        Assert.assertEquals(2, customers.size());
        Assert.assertTrue(customers.contains(customerAdam));
        Assert.assertTrue(customers.contains(customerMatus));
    }

    @Test
    public void deleteCustomerTest() {
        Customer customerAdam = new Customer("Adam", CustomerType.INDIVIDUAL);
        entityManager.persist(customerAdam);

        customerDao.delete(customerAdam);
        Assert.assertNull(entityManager.find(Customer.class, customerAdam.getId()));
    }

    @Test
    public void deleteNonExistingCustomerTest() {
        Customer customerAdam = new Customer("Adam", CustomerType.INDIVIDUAL);
        customerDao.delete(customerAdam);

        Assert.assertEquals(0, entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList().size());
    }

    @Test
    public void updateCustomerTest() {
        Customer customer = new Customer("Customer", CustomerType.INDIVIDUAL);
        entityManager.persist(customer);
        customer.setName("CustomerRenamed");
        customerDao.update(customer);

        Customer c = entityManager.find(Customer.class, customer.getId());
        Assert.assertNotNull(c);
        Assert.assertEquals(c, customer);
    }

    @Test
    public void updateNonExistingCustomerTest() {
        Customer customer = new Customer("Customer", CustomerType.INDIVIDUAL);
        customer.setName("CustomerRenamed");
        customerDao.update(customer);

        List<Customer> customers = entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        Assert.assertEquals(1, customers.size());
        Assert.assertTrue(customers.contains(customer));
    }
}
