package cz.muni.fi.pa165.project.service.facade;


import cz.muni.fi.pa165.project.dto.UserDTO;
import cz.muni.fi.pa165.project.entity.User;
import cz.muni.fi.pa165.project.enums.UserType;
import cz.muni.fi.pa165.project.facade.UserFacade;
import cz.muni.fi.pa165.project.service.BeanMappingService;
import cz.muni.fi.pa165.project.service.UserService;
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
 * Test for the {@link UserFacade}
 *
 * @author Martin Sisak, 445384
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserService userService;

    @Spy
    @Inject
    private BeanMappingService beanMappingService;

    @InjectMocks
    private UserFacade userFacade = new UserFacadeImpl();

    private User user1;
    private User user2;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createCustomers(){
        user1 = new User();
        user1.setName("Customer1");
        user1.setUserType(UserType.LEGAL_PERSON);
        user2 = new User();
        user2.setName("Customer2");
        user2.setUserType(UserType.INDIVIDUAL);
    }

    @Test
    public void getCustomerByIdTest(){
        when(userService.findById(user1.getId())).thenReturn(user1);
        UserDTO customerDTO = userFacade.getUserById(user1.getId());
        verify(userService, times(2)).findById(user1.getId());
        assertEquals(user1, beanMappingService.mapTo(customerDTO, User.class));
    }

    @Test
    public void getAllCustomersTest(){
        List<User> customersList = Arrays.asList(user1, user2);
        when(userService.findAll()).thenReturn(customersList);
        List<UserDTO> customerDTOList = userFacade.getAllUsers();
        verify(userService).findAll();
        List<User> customersResultList = beanMappingService.mapTo(customerDTOList, User.class);
        assertEquals(customersList, customersResultList);
    }

//    @Test
//    public void createCustomerTest(){
//        CustomerCreateDTO customerCreateDTO = new CustomerCreateDTO();
//        customerCreateDTO.setName("new customer");
//        customerCreateDTO.setUserType(UserType.INDIVIDUAL);
//        userFacade.registerUser(customerCreateDTO);
//        verify(userService).create(any(User.class));
//    }

    @Test
    public void deleteCustomerTest(){
        when(userService.findById(user1.getId())).thenReturn(user1);
        userFacade.deleteUser(user1.getId());
        verify(userService).findById(user1.getId());
        verify(userService).remove(user1);
    }

    @Test
    public void updateCustomerTest(){
        UserDTO customerCreateDTO = new UserDTO();
        customerCreateDTO.setName("User");
        customerCreateDTO.setUserType(UserType.LEGAL_PERSON);
        userFacade.updateUser(customerCreateDTO);
        verify(userService).update(any(User.class));
    }

    @Test
    public void getAllByCustomerTypeTest(){
        List<User> users = Arrays.asList(user1);
        when(userService.getAllByCustomerType(UserType.LEGAL_PERSON)).thenReturn(users);
        List<UserDTO> customerDTOS = userFacade.getAllByUserType(UserType.LEGAL_PERSON);
        verify(userService).getAllByCustomerType(UserType.LEGAL_PERSON);
        List<User> customersResult = beanMappingService.mapTo(customerDTOS, User.class);
        assertEquals(users, customersResult);
    }

}
