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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;


@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CustomerDaoImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public CustomerDao customerDao;

    private Customer customerAdam;
    private Customer customerMatus;

    @BeforeMethod
    public void setupBeforeTest() {
        customerAdam = new Customer("Adam", CustomerType.INDIVIDUAL);
        customerMatus = new Customer("Matus", CustomerType.LEGAL_PERSON);
    }

    @Test
    public void createCustomerTest() {
        customerDao.create(customerAdam);

        Customer customer = entityManager.find(Customer.class, customerAdam.getId());
        Assert.assertNotNull(customer);
        Assert.assertEquals(customer, customerAdam);
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void createCustomerWithNullAttribute() {
        Customer customer = new Customer(null, CustomerType.LEGAL_PERSON);
        customerDao.create(customer);
    }

    @Test
    public void findCustomerByIdTest() {
        entityManager.persist(customerAdam);

        Customer customer = customerDao.findById(customerAdam.getId());
        Assert.assertNotNull(customer);
        Assert.assertEquals(customer, customerAdam);
    }

    @Test
    public void findNonExistingCustomerTest() {
        Customer customer = customerDao.findById(0L);
        Assert.assertNull(customer);
    }

    @Test
    public void findAllCustomersTest() {
        entityManager.persist(customerAdam);
        entityManager.persist(customerMatus);

        List<Customer> customers = customerDao.findAll();
        Assert.assertEquals(2, customers.size());
        Assert.assertTrue(customers.contains(customerAdam));
        Assert.assertTrue(customers.contains(customerMatus));
    }

    @Test
    public void deleteCustomerTest() {
        entityManager.persist(customerAdam);

        customerDao.delete(customerAdam);
        Assert.assertNull(entityManager.find(Customer.class, customerAdam.getId()));
    }

    @Test
    public void deleteNonExistingCustomerTest() {
        customerDao.delete(customerAdam);

        Assert.assertEquals(0, entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList().size());
    }

    @Test
    public void updateCustomerTest() {
        entityManager.persist(customerAdam);
        customerAdam.setName("CustomerRenamed");
        customerDao.update(customerAdam);

        Customer c = entityManager.find(Customer.class, customerAdam.getId());
        Assert.assertNotNull(c);
        Assert.assertEquals(c, customerAdam);
    }
}
