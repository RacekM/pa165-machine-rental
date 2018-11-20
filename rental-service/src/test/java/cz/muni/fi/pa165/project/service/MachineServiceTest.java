package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.dao.MachineDao;
import cz.muni.fi.pa165.project.entity.Machine;
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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Tests for the {@link MachineService}.
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MachineServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MachineDao machineDao;

    @Inject
    @InjectMocks
    private MachineService machineService;

    private Machine testMachine;

    private Machine testMachineWithoutId;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareTestMachine() {
        testMachine = new Machine();
        //testMachine.setId(1);
        testMachine.setName("Testing machine");
        testMachineWithoutId = new Machine();
        //testMachine.setId(1);
        testMachineWithoutId.setName("Testing machine without assigned id");
    }

    @Test
    public void getByIdTest() {
        //todo implement setId method?
        long machineId = 1;
        when(machineDao.findById(machineId)).thenReturn(testMachine);
        Machine machine = machineService.findById(machineId);
        verify(machineDao).findById(machineId);
        assertEquals(testMachine, machine);
    }

    @Test
    public void getByInvalidIdTest() {
        long invalidId = -1;
        when(machineDao.findById(invalidId)).thenReturn(null);
        assertNull(machineService.findById(invalidId));
        verify(machineDao).findById(invalidId);
    }

    @Test
    public void getNonEmptyAllTest() {
        List<Machine> expectedMachines = Collections.singletonList(testMachine);
        when(machineDao.findAll()).thenReturn(Collections.unmodifiableList(expectedMachines));
        List<Machine> result = machineService.findAll();
        verify(machineDao).findAll();
        assertThat(result, is(expectedMachines));
    }

    @Test
    public void getEmptyALlTest() {
        List<Machine> expectedMachines = Collections.EMPTY_LIST;
        when(machineDao.findAll()).thenReturn(Collections.unmodifiableList(expectedMachines));
        List<Machine> result = machineService.findAll();
        verify(machineDao).findAll();
        assertThat(result, is(expectedMachines));
    }

    @Test
    public void createValidMachineTest() {
        long expectedId = 10;
        doAnswer(invocationOnMock -> {
            Machine machine = invocationOnMock.getArgument(0);
            //machine.setId(expectedId);
            return null;
        }).when(machineDao).create(testMachineWithoutId);
        machineService.create(testMachineWithoutId);
        verify(machineDao).create(testMachineWithoutId);
        assertEquals(expectedId, testMachineWithoutId.getId().longValue());
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createInvalidMachineTest() {
        testMachine.setName(null);
        doThrow(new DataIntegrityViolationException("")).when(machineDao).create(testMachine);
        machineService.create(testMachine);
    }

    @Test
    public void updateValidMachineTest() {
        doNothing().when(machineDao).update(testMachine);
        machineService.update(testMachine);
        verify(machineDao).update(testMachine);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void updateInvalidMachineTest() {
        testMachine.setName(null);
        doThrow(new DataIntegrityViolationException(""))
                .when(machineDao).update(testMachine);
        machineService.update(testMachine);
    }

    @Test
    public void deleteExistingMachine() {
        doNothing().when(machineDao).delete(testMachine);
        machineService.remove(testMachine);
        verify(machineDao).delete(testMachine);
    }

    @Test
    public void deleteNonExistingMachineTest() {
        doNothing().when(machineDao).delete(testMachine);
        machineService.remove(testMachine);
        verify(machineDao).delete(testMachine);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void deleteNullMachineTest() {
        doThrow(new InvalidDataAccessApiUsageException(""))
                .when(machineDao).delete(null);
        machineService.remove(null);
    }


}
