package cz.muni.fi.pa165.project.service;


import cz.muni.fi.pa165.project.dao.UserDao;
import cz.muni.fi.pa165.project.entity.User;
import cz.muni.fi.pa165.project.enums.UserType;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Test for the {@link UserService}
 *
 * @author Martin Sisak, 445384
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserDao userDao;

    @Inject
    @InjectMocks
    private UserService userService;

    private User user;
    private User userWithoutId;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createCustomers(){
        user = new User();
        user.setId(1L);
        user.setName("User");
        user.setUserType(UserType.INDIVIDUAL);
        userWithoutId = new User();
        userWithoutId.setName("User WithoutId");
        userWithoutId.setUserType(UserType.LEGAL_PERSON);
    }

    @Test
    public void getByIdTest(){
        when(userDao.findById(this.user.getId())).thenReturn(this.user);
        User user = userService.findById(this.user.getId());
        verify(userDao).findById(this.user.getId());
        assertEquals(this.user, user);
    }

    @Test
    public void getByNonExistingIdTest(){
        long id = -1;
        when(userDao.findById(id)).thenReturn(null);
        assertNull(userService.findById(id));
        verify(userDao).findById(id);
    }

    @Test
    public void getAllCustomersTest(){
        List<User> users = Collections.singletonList(user);
        when(userDao.findAll()).thenReturn(Collections.unmodifiableList(users));
        List<User> customers_result = userService.findAll();
        verify(userDao, atLeastOnce()).findAll();
        assertEquals(users, customers_result);

    }

    @Test
    public void getAllCustomersEmptyTest(){
        List<User> users = Collections.EMPTY_LIST;
        when(userDao.findAll()).thenReturn(Collections.unmodifiableList(users));
        List<User> customers_result = userService.findAll();
        verify(userDao, atLeastOnce()).findAll();
        assertEquals(users, customers_result);
    }

    @Test
    public void createValidCustomerTest(){
        doAnswer(invocationOnMock -> {
            User user = (User) invocationOnMock.getArguments()[0];
            user.setId(10L);
            return null;
        }).when(userDao).create(userWithoutId);
        userService.create(userWithoutId);
        verify(userDao).create(userWithoutId);
        assertNotNull(userWithoutId.getId());
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createInvalidCustomerTest(){
        user.setUserType(null);
        doThrow(new DataIntegrityViolationException("")).when(userDao).create(user);
        userService.create(user);

    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullCustomerTest(){
        doThrow(new NullPointerException()).when(userDao).create(null);
        userService.create(null);

    }

    @Test
    public void updateValidCustomerTest(){
        doNothing().when(userDao).update(user);
        userService.update(user);
        verify(userDao).update(user);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void updateInvalidCustomerTest(){
        user.setUserType(null);
        doThrow(new DataIntegrityViolationException("")).when(userDao).update(user);
        userService.update(user);
    }


    @Test(expectedExceptions = NullPointerException.class)
    public void updateNullCustomerTest(){
        doThrow(new NullPointerException()).when(userDao).update(null);
        userService.update(null);
    }

    @Test
    public void deleteExistingCustomerTest(){
        doNothing().when(userDao).delete(user);
        userService.remove(user);
        verify(userDao).delete(user);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void deleteNullCustomerTest(){
        doThrow(new InvalidDataAccessApiUsageException("")).when(userDao).delete(null);
        userService.remove(null);
    }

    @Test
    public void getAllByCustomerTypeTest(){
        List<User> users = Arrays.asList(userWithoutId, user);
        when(userDao.findAll()).thenReturn(Collections.unmodifiableList(users));
        List<User> customers1 = userService.getAllByCustomerType(UserType.INDIVIDUAL);
        verify(userDao, atLeastOnce()).findAll();
        assertNotEquals(users, customers1);
        assertEquals(1, customers1.size());
        assertTrue(customers1.contains(user));
        assertFalse(customers1.contains(userWithoutId));
    }

    @Test
    public void getAllByCustomerTypeWrongType(){
        List<User> users = Collections.singletonList(user);
        when(userDao.findAll()).thenReturn(Collections.unmodifiableList(users));
        List<User> customers1 = userService.getAllByCustomerType(UserType.LEGAL_PERSON);
        verify(userDao, atLeastOnce()).findAll();
        assertNotEquals(users, customers1);
        assertEquals(0, customers1.size());
        assertFalse(customers1.contains(user));
    }

    @Test
    public void getAllByCustomerNullType(){
        List<User> users = Collections.singletonList(user);
        when(userDao.findAll()).thenReturn(Collections.unmodifiableList(users));
        List<User> customers1 = userService.getAllByCustomerType(null);
        verify(userDao, atLeastOnce()).findAll();
        assertNotEquals(users, customers1);
        assertEquals(0, customers1.size());

    }

    @Test
    public void getAllByCustomerFromEmpty(){
        List<User> users = Collections.EMPTY_LIST;
        when(userDao.findAll()).thenReturn(Collections.unmodifiableList(users));
        List<User> customers1 = userService.getAllByCustomerType(UserType.INDIVIDUAL);
        verify(userDao, atLeastOnce()).findAll();
        assertEquals(customers1, users);
        assertEquals(0, customers1.size());
    }



}