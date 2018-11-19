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
import java.util.Calendar;
import java.util.List;

/**
 * Tests for RentalDao implementation
 * @author Juraj Vandor
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class RentalDaoImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public RentalDao rentalDao;

    private Calendar timeNow;
    private Calendar timeTomorrow;
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

        entityManager.persist(machineOne);
        entityManager.persist(machineTwo);
        entityManager.persist(customerAdam);
        entityManager.persist(customerMatus);

        timeNow = Calendar.getInstance();
        timeNow.add(Calendar.SECOND, -10);
        timeTomorrow = Calendar.getInstance();
        timeTomorrow.add(Calendar.DAY_OF_YEAR, 1);
        adamOne = new Rental(timeNow, timeTomorrow, "feedback", machineOne, customerAdam);

        adamTwo = new Rental(timeNow, timeTomorrow, "feedback", machineTwo, customerAdam);

        adamOneSecondTimeRented = new Rental(timeNow, timeTomorrow, "feedback", machineOne, customerAdam);
        matusOne = new Rental(timeNow, timeTomorrow, "feedback", machineOne, customerMatus);
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
        Rental rental = new Rental(null, timeTomorrow,
                "feedback", machineOne, customerAdam);
        rentalDao.create(rental);
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void createRentalWithNullAttributeTwo() {
        rentalDao.create(new Rental(timeNow, null, "feedback", machineOne, customerAdam));
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void createRentalWithNullAttributeThree() {
        rentalDao.create(new Rental(timeNow, timeTomorrow, null, machineOne, customerAdam));
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void createRentalWithNullAttributeFour() {
        rentalDao.create(new Rental(timeNow, timeTomorrow, "feedback", null, customerAdam));
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void createRentalWithNullAttributeFive() {
        rentalDao.create(new Rental(timeNow, timeTomorrow, "feedback", machineOne, null));
    }

    @Test
    public void findRentalByIdTest() {
        entityManager.persist(adamOne);

        Rental rental = rentalDao.findById(adamOne.getId());
        Assert.assertNotNull(rental);
        Assert.assertEquals(rental, adamOne);
    }

    @Test
    public void findNonExistingRentalTest() {
        Rental rental = rentalDao.findById(0L);
        Assert.assertNull(rental);
    }

    @Test
    public void findAllRentalsTest() {
        entityManager.persist(adamOne);
        entityManager.persist(adamTwo);
        entityManager.persist(adamOneSecondTimeRented);

        List<Rental> rentals = rentalDao.findAll();
        Assert.assertEquals(3, rentals.size());
        Assert.assertTrue(rentals.contains(adamOne));
        Assert.assertTrue(rentals.contains(adamTwo));
        Assert.assertTrue(rentals.contains(adamOneSecondTimeRented));
    }

    @Test
    public void deleteCustomerTest() {
        entityManager.persist(adamOne);
        rentalDao.delete(adamOne);
        Assert.assertNull(entityManager.find(Rental.class, adamOne.getId()));
    }

    @Test
    public void deleteNonExistingCustomerTest() {
        rentalDao.delete(adamOne);
        Assert.assertEquals(0, entityManager.createQuery("SELECT r FROM Rental r", Rental.class).getResultList().size());
    }

    @Test
    public void updateRentalTest() {
        entityManager.persist(adamOne);
        adamOne.setFeedback("new feedback");
        rentalDao.update(adamOne);

        Rental rental = entityManager.find(Rental.class, adamOne.getId());
        Assert.assertNotNull(rental);
        Assert.assertEquals(rental, adamOne);
    }
}
