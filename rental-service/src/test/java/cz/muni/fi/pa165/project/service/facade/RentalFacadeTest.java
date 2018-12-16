package cz.muni.fi.pa165.project.service.facade;

import cz.muni.fi.pa165.project.dto.*;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Rental;
import cz.muni.fi.pa165.project.entity.Revision;
import cz.muni.fi.pa165.project.entity.User;
import cz.muni.fi.pa165.project.enums.UserType;
import cz.muni.fi.pa165.project.facade.RentalFacade;
import cz.muni.fi.pa165.project.service.BeanMappingService;
import cz.muni.fi.pa165.project.service.MachineService;
import cz.muni.fi.pa165.project.service.RentalService;
import cz.muni.fi.pa165.project.service.UserService;
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
    private UserService userService;

    @Mock
    private MachineService machineService;

    @Mock
    private RentalService rentalService;

    @Spy
    @Inject
    private BeanMappingService beanMappingService;

    @InjectMocks
    private RentalFacade rentalFacade = new RentalFacadeImpl();

    private User testingUser;
    private Machine testingMachine;

    private Rental testingRental1;
    private Rental testingRental2;
    private Rental testingRental3;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testingUser = new User();
        testingUser.setId(1L);
        testingUser.setName("TestCustomer");
        testingUser.setUserType(UserType.INDIVIDUAL);

        testingMachine = new Machine();
        testingMachine.setId(1L);
        testingMachine.setName("Chainsaw");

        testingRental1 = new Rental();
        testingRental1.setId(1L);
        testingRental1.setDateOfRental(LocalDateTime.now().minusDays(1));
        testingRental1.setReturnDate(LocalDateTime.now().plusDays(1));
        testingRental1.setFeedback("Feedback 1");
        testingRental1.setUser(testingUser);
        testingRental1.setMachine(testingMachine);

        testingRental2 = new Rental();
        testingRental2.setId(2L);
        testingRental2.setDateOfRental(LocalDateTime.now().minusDays(1));
        testingRental2.setReturnDate(LocalDateTime.now().plusDays(1));
        testingRental2.setFeedback("Feedback 2");
        testingRental2.setUser(testingUser);
        testingRental2.setMachine(testingMachine);

        testingRental3 = new Rental();
        testingRental3.setId(3L);
        testingRental3.setDateOfRental(LocalDateTime.now().minusDays(1));
        testingRental3.setReturnDate(LocalDateTime.now().plusDays(1));
        testingRental3.setFeedback("Feedback 3");
        testingRental3.setUser(testingUser);
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
        User user = new User();
        user.setId(1L);
        List<Rental> rentals = Arrays.asList(testingRental1, testingRental2, testingRental3);
        user.setName("Adam");
        when(userService.findById(user.getId())).thenReturn(user);
        when(rentalService.findByCustomer(user)).thenReturn(rentals);

        List<RentalDTO> returnedRentals = rentalFacade.getRentalsByUser(user.getId());
        List<Rental> resultRental = beanMappingService.mapTo(returnedRentals, Rental.class);

        assertThat(resultRental, is(rentals));
        verify(userService).findById(user.getId());
        verify(rentalService).findByCustomer(user);

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
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("CustomerDTO 1");
        userDTO.setUserType(UserType.INDIVIDUAL);

        MachineDTO machineDTO = new MachineDTO();
        machineDTO.setId(1L);
        machineDTO.setName("MachineDTO 1");

        RentalCreateDTO rentalCreateDTO = new RentalCreateDTO();
        rentalCreateDTO.setDateOfRental(LocalDateTime.now().minusDays(1));
        rentalCreateDTO.setReturnDate(LocalDateTime.now().plusDays(1));
        rentalCreateDTO.setFeedback("Feedback 3");
        rentalCreateDTO.setUser(userDTO.getId());
        rentalCreateDTO.setMachine(machineDTO.getId());

        userService.create(beanMappingService.mapTo(userDTO, User.class));
        machineService.create(beanMappingService.mapTo(machineDTO, Machine.class));

        when(rentalService.isValid(any(Rental.class))).thenReturn(true);
        rentalFacade.createRental(rentalCreateDTO);
        verify(userService,atLeastOnce()).findById(userDTO.getId());
        verify(machineService, atLeastOnce()).findById(machineDTO.getId());
        verify(rentalService, atLeastOnce()).create(any(Rental.class));
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

    /**
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void isValidRentalInvalidDatesTest() {
        testingRental1.setDateOfRental(LocalDateTime.now().plusDays(1));
        testingRental1.setReturnDate(LocalDateTime.now().minusDays(1));

        when(rentalService.isValid(any(Rental.class))).thenReturn(false);
        rentalFacade.createRental(beanMappingService.mapTo(testingRental1, RentalCreateDTO.class));
        verify(rentalService).isValid(any(Rental.class));
    }
     */

    @Test
    public void activeRentalsWithLastRevisionByCustomerTest(){
        Map<Rental, Revision> rentalRevisionMap = new HashMap<>();
        when(rentalService.activeRentalsWithLastRevisionByCustomer(testingUser)).thenReturn(rentalRevisionMap);

        UserDTO customerDTO = new UserDTO();
        customerDTO.setId(1L);
        customerDTO.setUserType(UserType.INDIVIDUAL);
        customerDTO.setName("TestCustomer");

        Map<RentalDTO, RevisionDTO> rentalDTORevisionDTOMap = rentalFacade.activeRentalsWithLastRevisionByCustomer(customerDTO);
        verify(rentalService).activeRentalsWithLastRevisionByCustomer(testingUser);
        Map<Rental, Revision> rentalRevisionMapResult = beanMappingService.mapTo(rentalDTORevisionDTOMap, Rental.class, Revision.class);
        assertEquals(rentalRevisionMap, rentalRevisionMapResult);

    }

}
