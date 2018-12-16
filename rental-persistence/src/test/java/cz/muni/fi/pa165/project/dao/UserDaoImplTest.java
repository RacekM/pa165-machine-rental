package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.PersistenceApplicationContext;
import cz.muni.fi.pa165.project.entity.User;
import cz.muni.fi.pa165.project.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import java.util.List;


/**
 * Tests for CustomerDaoImpl.
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public UserDao userDao;

    private User userAdam;
    private User userMatus;

    @BeforeMethod
    public void setupBeforeTest() {
        userAdam = new User("Adam", UserType.INDIVIDUAL);
        userMatus = new User("Matus", UserType.LEGAL_PERSON);
    }

    @Test
    public void createCustomerTest() {
        userDao.create(userAdam);

        User user = entityManager.find(User.class, userAdam.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals(user, userAdam);
    }

    @Test(expectedExceptions = {DataAccessException.class})
    public void createCustomerWithNullAttribute() {
        User user = new User(null, UserType.LEGAL_PERSON);
        userDao.create(user);
    }

    @Test
    public void findCustomerByIdTest() {
        entityManager.persist(userAdam);

        User user = userDao.findById(userAdam.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals(user, userAdam);
    }

    @Test
    public void findNonExistingCustomerTest() {
        User user = userDao.findById(0L);
        Assert.assertNull(user);
    }

    @Test
    public void findAllCustomersTest() {
        entityManager.persist(userAdam);
        entityManager.persist(userMatus);

        List<User> users = userDao.findAll();
        Assert.assertEquals(2, users.size());
        Assert.assertTrue(users.contains(userAdam));
        Assert.assertTrue(users.contains(userMatus));
    }

    @Test
    public void deleteCustomerTest() {
        entityManager.persist(userAdam);

        userDao.delete(userAdam);
        Assert.assertNull(entityManager.find(User.class, userAdam.getId()));
    }

    @Test
    public void deleteNonExistingCustomerTest() {
        userDao.delete(userAdam);

        Assert.assertEquals(0, entityManager.createQuery("SELECT c FROM Users c", User.class).getResultList().size());
    }

    @Test
    public void updateCustomerTest() {
        entityManager.persist(userAdam);
        userAdam.setName("CustomerRenamed");
        userDao.update(userAdam);

        User c = entityManager.find(User.class, userAdam.getId());
        Assert.assertNotNull(c);
        Assert.assertEquals(c, userAdam);
    }

}
