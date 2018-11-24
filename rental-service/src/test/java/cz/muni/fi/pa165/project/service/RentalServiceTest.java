package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.dao.RentalDao;
import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Rental;
import cz.muni.fi.pa165.project.enums.CustomerType;
import cz.muni.fi.pa165.project.service.configuration.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * Tests for the {@link RentalService}.
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RentalServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private RentalDao rentalDao;

    @Autowired
    @InjectMocks
    private RentalService rentalService;

    private Rental testRental;
    private Rental testRentalWithoutId;
    private List<Rental> testExistingRentals;

    @BeforeMethod
    public void preparetestRental() {
        MockitoAnnotations.initMocks(this);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("TestCustomer");
        customer.setCustomerType(CustomerType.INDIVIDUAL);

        Machine machine = new Machine();
        machine.setId(1L);
        machine.setName("Chainsaw");

        testRental = new Rental();
        testRental.setId(1L);
        testRental.setDateOfRental(LocalDateTime.now().minusDays(1));
        testRental.setReturnDate(LocalDateTime.now().plusDays(1));
        testRental.setFeedback("TestFeedback.");
        testRental.setCustomer(customer);
        testRental.setMachine(machine);

        testRentalWithoutId = new Rental();
        testRentalWithoutId.setDateOfRental(LocalDateTime.now().minusDays(1));
        testRentalWithoutId.setReturnDate(LocalDateTime.now().plusDays(1));
        testRentalWithoutId.setFeedback("TestFeedback.");
        testRentalWithoutId.setCustomer(customer);
        testRentalWithoutId.setMachine(machine);

        Rental testRentalFuture = new Rental();
        testRentalFuture.setId(3L);
        testRentalFuture.setDateOfRental(LocalDateTime.now().minusDays(3));
        testRentalFuture.setReturnDate(LocalDateTime.now().minusDays(2));
        testRentalFuture.setFeedback("TestFeedback.");
        testRentalFuture.setCustomer(customer);
        testRentalFuture.setMachine(machine);

        Rental testRentalPast = new Rental();
        testRentalPast.setId(4L);
        testRentalPast.setDateOfRental(LocalDateTime.now().plusDays(2));
        testRentalPast.setReturnDate(LocalDateTime.now().plusDays(3));
        testRentalPast.setFeedback("TestFeedback.");
        testRentalPast.setCustomer(customer);
        testRentalPast.setMachine(machine);

        testExistingRentals = new ArrayList<>();
        testExistingRentals.add(testRentalFuture);
        testExistingRentals.add(testRentalPast);
    }

    @Test
    public void getByIdTest() {
        when(rentalDao.findById(testRental.getId())).thenReturn(testRental);
        Rental rental = rentalService.findById(testRental.getId());
        verify(rentalDao).findById(testRental.getId());
        assertEquals(testRental, rental);
    }

    @Test
    public void getByInvalidIdTest() {
        long invalidId = -1;
        when(rentalDao.findById(invalidId)).thenReturn(null);
        Assert.assertNull(rentalService.findById(invalidId));
        verify(rentalDao).findById(invalidId);
    }

    @Test
    public void getNonEmptyAllTest() {
        List<Rental> expectedRentals = Collections.singletonList(testRental);
        when(rentalDao.findAll()).thenReturn(Collections.unmodifiableList(expectedRentals));
        List<Rental> result = rentalService.findAll();
        verify(rentalDao).findAll();
        assertThat(result, is(expectedRentals));
    }

    @Test
    public void getEmptyAllTest() {
        List<Rental> expectedRentals = Collections.emptyList();
        when(rentalDao.findAll()).thenReturn(Collections.unmodifiableList(expectedRentals));
        List<Rental> result = rentalService.findAll();
        verify(rentalDao).findAll();
        assertThat(result, is(expectedRentals));
    }

    @Test
    public void createValidRentalTest() {
        long expectedId = 10;
        doAnswer(invocationOnMock -> {
            Rental rental = invocationOnMock.getArgument(0);
            rental.setId(expectedId);
            return null;
        }).when(rentalDao).create(testRentalWithoutId);
        rentalService.create(testRentalWithoutId);
        verify(rentalDao).create(testRentalWithoutId);
        assertEquals(expectedId, testRentalWithoutId.getId().longValue());
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createRentalWithInvalidDateOfRentalFutureTest() {
        testRental.setDateOfRental(LocalDateTime.now().plusDays(1));
        doThrow(new DataIntegrityViolationException("")).when(rentalDao).create(testRental);
        rentalService.create(testRental);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createRentalWithInvalidDateOfRentalNullTest() {
        testRental.setDateOfRental(null);
        doThrow(new DataIntegrityViolationException("")).when(rentalDao).create(testRental);
        rentalService.create(testRental);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createRentalWithInvalidReturnDateNullTest() {
        testRental.setReturnDate(null);
        doThrow(new DataIntegrityViolationException("")).when(rentalDao).create(testRental);
        rentalService.create(testRental);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createRentalWithInvalidFeedbackNullTest() {
        testRental.setFeedback(null);
        doThrow(new DataIntegrityViolationException("")).when(rentalDao).create(testRental);
        rentalService.create(testRental);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createRentalWithInvalidMachineNullTest() {
        testRental.setMachine(null);
        doThrow(new DataIntegrityViolationException("")).when(rentalDao).create(testRental);
        rentalService.create(testRental);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createRentalWithInvalidCustomerNullTest() {
        testRental.setCustomer(null);
        doThrow(new DataIntegrityViolationException("")).when(rentalDao).create(testRental);
        rentalService.create(testRental);
    }

    @Test
    public void updateValidRentalTest() {
        doNothing().when(rentalDao).update(testRental);
        rentalService.update(testRental);
        verify(rentalDao).update(testRental);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void updateRentalWithInvalidDateOfRentalFutureTest() {
        testRental.setDateOfRental(LocalDateTime.now().plusDays(1));
        doThrow(new DataIntegrityViolationException("")).when(rentalDao).update(testRental);
        rentalService.update(testRental);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void updateRentalWithInvalidDateOfRentalNullTest() {
        testRental.setDateOfRental(null);
        doThrow(new DataIntegrityViolationException("")).when(rentalDao).update(testRental);
        rentalService.update(testRental);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void updateRentalWithInvalidReturnDateNullTest() {
        testRental.setReturnDate(null);
        doThrow(new DataIntegrityViolationException("")).when(rentalDao).update(testRental);
        rentalService.update(testRental);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void updateRentalWithInvalidFeedbackNullTest() {
        testRental.setFeedback(null);
        doThrow(new DataIntegrityViolationException("")).when(rentalDao).update(testRental);
        rentalService.update(testRental);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void updateRentalWithInvalidMachineNullTest() {
        testRental.setMachine(null);
        doThrow(new DataIntegrityViolationException("")).when(rentalDao).update(testRental);
        rentalService.update(testRental);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void updateRentalWithInvalidCustomerNullTest() {
        testRental.setCustomer(null);
        doThrow(new DataIntegrityViolationException("")).when(rentalDao).update(testRental);
        rentalService.update(testRental);
    }

    @Test
    public void deleteExistingRentalTest() {
        doNothing().when(rentalDao).delete(testRental);
        rentalService.remove(testRental);
        verify(rentalDao).delete(testRental);
    }

    @Test
    public void deleteNonExistingRentalTest() {
        doNothing().when(rentalDao).delete(testRental);
        rentalService.remove(testRental);
        verify(rentalDao).delete(testRental);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void deleteNullRentalTest() {
        doThrow(new InvalidDataAccessApiUsageException("")).when(rentalDao).delete(null);
        rentalService.remove(null);
    }

    @Test
    public void changeFeedbackTest() {
        rentalService.changeFeedback(testRental, "NewFeedback");
        verify(rentalDao).update(testRental);
        assertEquals(testRental.getFeedback(), "NewFeedback");
    }

    @Test
    public void isValidCorrectDatesTest() {
        testRental.setDateOfRental(LocalDateTime.now());
        testRental.setReturnDate(LocalDateTime.now().plusDays(1));

        when(rentalDao.findAll()).thenReturn(new ArrayList<>());
        Assert.assertTrue(rentalService.isValid(testRental));
    }

    @Test
    public void isValidIncorrectDatesWrongTimelineTest() {
        testRental.setDateOfRental(LocalDateTime.now().plusDays(1));
        testRental.setReturnDate(LocalDateTime.now());

        when(rentalDao.findAll()).thenReturn(new ArrayList<>());
        Assert.assertFalse(rentalService.isValid(testRental));
    }

    @Test
    public void isValidIncorrectDatesSameDateTimeTest() {
        LocalDateTime dateTime = LocalDateTime.now();
        testRental.setDateOfRental(dateTime);
        testRental.setReturnDate(dateTime);

        when(rentalDao.findAll()).thenReturn(new ArrayList<>());
        Assert.assertFalse(rentalService.isValid(testRental));
    }

    @Test
    public void isValidNotOverlappingTest() {
        testRental.setDateOfRental(LocalDateTime.now().minusDays(1));
        testRental.setReturnDate(LocalDateTime.now().plusDays(1));

        when(rentalDao.findAll()).thenReturn(testExistingRentals);
        Assert.assertTrue(rentalService.isValid(testRental));
    }

    @Test
    public void isValidPastOverlapingTest() {
        testRental.setDateOfRental(LocalDateTime.now().minusDays(2));
        testRental.setReturnDate(LocalDateTime.now().plusDays(1));

        when(rentalDao.findAll()).thenReturn(testExistingRentals);
        Assert.assertFalse(rentalService.isValid(testRental));
    }

    @Test
    public void isValidFutureOverlappingTest() {
        testRental.setDateOfRental(LocalDateTime.now().minusDays(1));
        testRental.setReturnDate(LocalDateTime.now().plusDays(2));

        when(rentalDao.findAll()).thenReturn(testExistingRentals);
        Assert.assertFalse(rentalService.isValid(testRental));
    }

    @Test
    public void isValidWholeIntervalsOverlappingTest() {
        testRental.setDateOfRental(LocalDateTime.now().minusDays(4));
        testRental.setReturnDate(LocalDateTime.now().plusDays(4));

        when(rentalDao.findAll()).thenReturn(testExistingRentals);
        Assert.assertFalse(rentalService.isValid(testRental));
    }

}
