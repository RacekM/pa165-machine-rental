package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.PersistenceApplicationContext;
import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Rental;
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
import java.util.Date;
import java.util.List;


@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class RentalDaoImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public RentalDao rentalDao;
    @Autowired
    public MachineDao machineDao;
    @Autowired
    public CustomerDao customerDao;

    private Customer customerAdam;
    private Customer customerMatus;
    private Machine machineOne;
    private Machine machineTwo;
    private Rental adamOne;
    private Rental matusOne;
    private Rental adamTwo;
    private Rental adamOneSecondTimeRented;

    @BeforeMethod
    public void setupBeforeTest() {
        customerAdam = new Customer("Adam", CustomerType.INDIVIDUAL);
        customerMatus = new Customer("Matus", CustomerType.LEGAL_PERSON);
        machineOne = new Machine("drill");
        machineTwo = new Machine("saw");

        machineDao.create(machineOne);
        machineDao.create(machineTwo);
        customerDao.create(customerAdam);
        customerDao.create(customerMatus);

        adamOne = new Rental(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 24*60*60*1000),
                "feedback", machineOne, customerAdam);

        adamTwo = new Rental(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 24*60*60*1000),
                "feedback", machineTwo, customerAdam);

        adamOneSecondTimeRented = new Rental(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 24*60*60*1000),
                "feedback", machineOne, customerAdam);
        matusOne = new Rental(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 24*60*60*1000),
                "feedback", machineOne, customerMatus);
    }

    @Test
    public void createRentalTest() {
        rentalDao.create(adamOne);
        rentalDao.create(adamTwo);
        rentalDao.create(adamOneSecondTimeRented);
        rentalDao.create(matusOne);
        Rental rental = entityManager.find(Rental.class, adamOne.getId());
        Assert.assertNotNull(rental);
        Assert.assertEquals(rental, adamOne);

        rental = entityManager.find(Rental.class, adamTwo.getId());
        Assert.assertNotNull(rental);
        Assert.assertEquals(rental, adamTwo);

        rental = entityManager.find(Rental.class, adamOneSecondTimeRented.getId());
        Assert.assertNotNull(rental);
        Assert.assertEquals(rental, adamOneSecondTimeRented);

        rental = entityManager.find(Rental.class, matusOne.getId());
        Assert.assertNotNull(rental);
        Assert.assertEquals(rental, matusOne);
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void createRentalWithNullAttributeOne() {
        Rental rental = new Rental(null, new Date(System.currentTimeMillis() + 24*60*60*1000),
                "feedback", machineOne, customerAdam);
        rentalDao.create(rental);
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void createRentalWithNullAttributeTwo() {
        rentalDao.create(new Rental(new Date(System.currentTimeMillis()), null, "feedback", machineOne, customerAdam));
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void createRentalWithNullAttributeThree() {
        rentalDao.create(new Rental(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 24*60*60*1000),
                null, machineOne, customerAdam));
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void createRentalWithNullAttributeFour() {
        rentalDao.create(new Rental(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 24*60*60*1000),
                "feedback", null, customerAdam));
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void createRentalWithNullAttributeFive() {
        rentalDao.create(new Rental(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 24*60*60*1000),
                "feedback", machineOne, null));
    }

}
