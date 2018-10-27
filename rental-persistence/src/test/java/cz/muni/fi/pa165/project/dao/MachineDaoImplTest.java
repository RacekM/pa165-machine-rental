package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.PersistenceApplicationContext;
import cz.muni.fi.pa165.project.entity.Machine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Tests for MachineDao implementation
 * @author Martin Sisak (445384@mail.muni.cz)
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MachineDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MachineDao machineDao;

    private Machine truck;
    private Machine bulldozer;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeMethod
    public void setUp(){
        this.truck = new Machine("Truck");
        this.bulldozer = new Machine("Bulldozer");
    }

    @Test
    public void createMachineTest(){
        machineDao.create(truck);
        assertNotNull(truck);
        assertEquals(truck, entityManager.find(Machine.class, truck.getId()));
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void createMachineIsNull() {
        machineDao.create(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createMachineWithNullName(){
        machineDao.create(new Machine(null));

    }

    @Test
    public void findMachineByIdTest(){
        entityManager.persist(truck);
        Machine machine = machineDao.findById(truck.getId());
        assertNotNull(machine);
        assertEquals(truck, machine);
    }

    @Test
    public void findNonExistingMachineTest(){
        Machine machine = machineDao.findById(0L);
        assertNull(machine);
    }

    @Test
    public void findAllMachinesTest(){
        entityManager.persist(truck);
        entityManager.persist(bulldozer);
        List<Machine> machines = machineDao.findAll();
        assertEquals(2, machines.size());
        assertTrue(machines.contains(truck));
        assertTrue(machines.contains(bulldozer));

    }

    @Test
    public void updateMachineTest(){
        entityManager.persist(truck);
        assertNotNull(entityManager.find(Machine.class, truck.getId()));
        truck.setName("Vehicle");
        machineDao.update(truck);

        Machine machine = entityManager.find(Machine.class, truck.getId());
        assertEquals(truck, machine);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void updateMachineIsNull() {
        machineDao.update(null);
    }

    @Test
    public void deleteMachineTest(){
        entityManager.persist(truck);
        assertNotNull(entityManager.find(Machine.class, truck.getId()));

        machineDao.delete(truck);
        assertNull(entityManager.find(Machine.class, truck.getId()));
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void deleteMachineIsNull() {
        machineDao.delete(null);
    }

    @Test
    public void deleteNonExistingMachine(){
        machineDao.delete(truck);
        assertEquals(0, entityManager.createQuery("SELECT m FROM Machine m", Machine.class).getResultList().size());
    }

}
