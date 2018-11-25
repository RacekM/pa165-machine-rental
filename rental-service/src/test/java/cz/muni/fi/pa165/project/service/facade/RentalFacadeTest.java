package cz.muni.fi.pa165.project.service.facade;

import cz.muni.fi.pa165.project.dto.*;
import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Rental;
import cz.muni.fi.pa165.project.entity.Revision;
import cz.muni.fi.pa165.project.enums.CustomerType;
import cz.muni.fi.pa165.project.facade.RentalFacade;
import cz.muni.fi.pa165.project.service.BeanMappingService;
import cz.muni.fi.pa165.project.service.CustomerService;
import cz.muni.fi.pa165.project.service.MachineService;
import cz.muni.fi.pa165.project.service.RentalService;
import cz.muni.fi.pa165.project.service.configuration.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Tests for the {@link RentalFacade}.
 *
 * @author Adam Vanko (44530@mail.muni.cz)
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RentalFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CustomerService customerService;

    @Mock
    private MachineService machineService;

    @Mock
    private RentalService rentalService;

    @Spy
    @Inject
    private BeanMappingService beanMappingService;

    @InjectMocks
    private RentalFacade rentalFacade = new RentalFacadeImpl();

    private Customer testingCustomer;
    private Machine testingMachine;

    private Rental testingRental1;
    private Rental testingRental2;
    private Rental testingRental3;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testingCustomer = new Customer();
        testingCustomer.setId(1L);
        testingCustomer.setName("TestCustomer");
        testingCustomer.setCustomerType(CustomerType.INDIVIDUAL);

        testingMachine = new Machine();
        testingMachine.setId(1L);
        testingMachine.setName("Chainsaw");

        testingRental1 = new Rental();
        testingRental1.setId(1L);
        testingRental1.setDateOfRental(LocalDateTime.now().minusDays(1));
        testingRental1.setReturnDate(LocalDateTime.now().plusDays(1));
        testingRental1.setFeedback("Feedback 1");
        testingRental1.setCustomer(testingCustomer);
        testingRental1.setMachine(testingMachine);

        testingRental2 = new Rental();
        testingRental2.setId(2L);
        testingRental2.setDateOfRental(LocalDateTime.now().minusDays(1));
        testingRental2.setReturnDate(LocalDateTime.now().plusDays(1));
        testingRental2.setFeedback("Feedback 2");
        testingRental2.setCustomer(testingCustomer);
        testingRental2.setMachine(testingMachine);

        testingRental3 = new Rental();
        testingRental3.setId(3L);
        testingRental3.setDateOfRental(LocalDateTime.now().minusDays(1));
        testingRental3.setReturnDate(LocalDateTime.now().plusDays(1));
        testingRental3.setFeedback("Feedback 3");
        testingRental3.setCustomer(testingCustomer);
        testingRental3.setMachine(testingMachine);
    }

    @Test
    public void rentalMappingTest() {
        RentalDTO rentalDTO = beanMappingService.mapTo(testingRental1, RentalDTO.class);
        Rental rental = beanMappingService.mapTo(rentalDTO, Rental.class);
        assertEquals(rental, testingRental1);
    }

    @Test
    public void getRentalByIdTest() {
        when(rentalService.findById(testingRental1.getId()))
                .thenReturn(testingRental1);
        RentalDTO rentalDTO = rentalFacade.getRentalById(testingRental1.getId());
        verify(rentalService).findById(testingRental1.getId());
        assertEquals(testingRental1, beanMappingService.mapTo(rentalDTO, Rental.class));
    }

    @Test
    public void getRentalsByCustomerTest() {
        Customer customer = new Customer();
        customer.setId(1L);
        List<Rental> rentals = Arrays.asList(testingRental1, testingRental2, testingRental3);
        customer.setName("Adam");
        when(customerService.findById(customer.getId())).thenReturn(customer);
        when(rentalService.findByCustomer(customer)).thenReturn(rentals);

        List<RentalDTO> returnedRentals = rentalFacade.getRentalsByCustomer(customer.getId());
        List<Rental> resultRental = beanMappingService.mapTo(returnedRentals, Rental.class);

        assertThat(resultRental, is(rentals));
        verify(customerService).findById(customer.getId());
        verify(rentalService).findByCustomer(customer);

    }

    @Test
    public void getAllRentalsTest() {
        List<Rental> inputArray = Arrays.asList(testingRental1, testingRental1, testingRental3);
        when(rentalService.findAll())
                .thenReturn(inputArray);
        List<RentalDTO> resultDTO = rentalFacade.getAllRentals();
        verify(rentalService).findAll();
        List<Rental> resultRental = beanMappingService.mapTo(resultDTO, Rental.class);
        assertThat(resultRental, is(inputArray));
    }

    @Test
    public void createRentalTest() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("CustomerDTO 1");
        customerDTO.setCustomerType(CustomerType.INDIVIDUAL);

        MachineDTO machineDTO = new MachineDTO();
        machineDTO.setId(1L);
        machineDTO.setName("MachineDTO 1");

        RentalCreateDTO rentalCreateDTO = new RentalCreateDTO();
        rentalCreateDTO.setDateOfRental(LocalDateTime.now().minusDays(1));
        rentalCreateDTO.setReturnDate(LocalDateTime.now().plusDays(1));
        rentalCreateDTO.setFeedback("Feedback 3");
        rentalCreateDTO.setCustomer(customerDTO);
        rentalCreateDTO.setMachine(machineDTO);

        customerService.create(beanMappingService.mapTo(customerDTO, Customer.class));
        machineService.create(beanMappingService.mapTo(machineDTO, Machine.class));

        when(rentalService.isValid(any(Rental.class))).thenReturn(true);
        rentalFacade.createRental(rentalCreateDTO);
        verify(customerService).findById(customerDTO.getId());
        verify(machineService).findById(machineDTO.getId());
        verify(rentalService).create(any(Rental.class));
    }

    @Test
    public void deleteRentalTest() {
        when(rentalService.findById(testingRental1.getId())).thenReturn(testingRental1);
        rentalFacade.deleteRental(testingRental1.getId());
        verify(rentalService).findById(testingRental1.getId());
        verify(rentalService).remove(testingRental1);
    }

    @Test
    public void changeFeedbackTest() {
        String newFeedback = "NewFeedback";
        doAnswer(invocationOnMock -> {
            testingRental1.setFeedback(newFeedback);
            return null;
        }).when(rentalService).changeFeedback(testingRental1, newFeedback);

        RentalChangeFeedbackDTO rentalChangeFeedbackDTO = new RentalChangeFeedbackDTO();
        rentalChangeFeedbackDTO.setRental(beanMappingService.mapTo(testingRental1, RentalDTO.class));
        rentalChangeFeedbackDTO.setFeedback(newFeedback);

        rentalFacade.changeRentalFeedback(rentalChangeFeedbackDTO);
        verify(rentalService).changeFeedback(any(Rental.class), eq(newFeedback));
        assertEquals(testingRental1.getFeedback(), newFeedback);
    }


    @Test
    public void isValidRentalTest() {
        RentalCreateDTO rentalCreateDTO = new RentalCreateDTO();

        when(rentalService.isValid(any(Rental.class))).thenReturn(true);
        assertTrue(rentalFacade.isValidRental(rentalCreateDTO));
        verify(rentalService).isValid(any(Rental.class));
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void isValidRentalInvalidDatesTest() {
        testingRental1.setDateOfRental(LocalDateTime.now().plusDays(1));
        testingRental1.setReturnDate(LocalDateTime.now().minusDays(1));

        when(rentalService.isValid(any(Rental.class))).thenReturn(false);
        rentalFacade.createRental(beanMappingService.mapTo(testingRental1, RentalCreateDTO.class));
        verify(rentalService).isValid(any(Rental.class));
    }

    @Test
    public void activeRentalsWithLastRevisionByCustomerTest(){
        Map<Rental, Revision> rentalRevisionMap = new HashMap<>();
        when(rentalService.activeRentalsWithLastRevisionByCustomer(testingCustomer)).thenReturn(rentalRevisionMap);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setCustomerType(CustomerType.INDIVIDUAL);
        customerDTO.setName("TestCustomer");

        Map<RentalDTO, RevisionDTO> rentalDTORevisionDTOMap = rentalFacade.activeRentalsWithLastRevisionByCustomer(customerDTO);
        verify(rentalService).activeRentalsWithLastRevisionByCustomer(testingCustomer);
        Map<Rental, Revision> rentalRevisionMapResult = beanMappingService.mapTo(rentalDTORevisionDTOMap, Rental.class, Revision.class);
        assertEquals(rentalRevisionMap, rentalRevisionMapResult);

    }

}
