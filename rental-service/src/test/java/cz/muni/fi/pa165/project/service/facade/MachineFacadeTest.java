package cz.muni.fi.pa165.project.service.facade;

import cz.muni.fi.pa165.project.dto.MachineCreateDTO;
import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.facade.MachineFacade;
import cz.muni.fi.pa165.project.service.BeanMappingService;
import cz.muni.fi.pa165.project.service.MachineService;
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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Tests for the {@link MachineFacade}.
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MachineFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MachineService machineService;

    @Spy
    @Inject
    private BeanMappingService beanMappingService;

    @InjectMocks
    private MachineFacade machineFacade = new MachineFacadeImpl();

    private Machine testingMachine1;
    private Machine testingMachine2;
    private Machine testingMachine3;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testingMachine1 = new Machine();
        testingMachine1.setId(1L);
        testingMachine1.setName("Machine 1");

        testingMachine2 = new Machine();
        testingMachine2.setId(2L);
        testingMachine2.setName("Machine 2");

        testingMachine3 = new Machine();
        testingMachine3.setId(3L);
        testingMachine3.setName("Machine 3");
    }

    @Test
    public void getMachineByIdTest() {
        when(machineService.findById(testingMachine1.getId()))
                .thenReturn(testingMachine1);
        MachineDTO machineDTO = machineFacade.getMachineById(testingMachine1.getId());
        verify(machineService).findById(testingMachine1.getId());
        assertEquals(testingMachine1, beanMappingService.mapTo(machineDTO, Machine.class));
    }

    @Test
    public void getAllMachinesTest() {
        List<Machine> inputArray = Arrays.asList(testingMachine1, testingMachine1, testingMachine3);
        when(machineService.findAll())
                .thenReturn(inputArray);
        List<MachineDTO> resultDTO = machineFacade.getAllMachines();
        verify(machineService).findAll();
        List<Machine> resultMachine = beanMappingService.mapTo(resultDTO, Machine.class);
        assertThat(resultMachine, is(inputArray));
    }

    @Test
    public void createMachineTest() {
        MachineCreateDTO machineCreateDTO = new MachineCreateDTO();
        machineCreateDTO.setName("machine create");
        machineFacade.createMachine(machineCreateDTO);
        verify(machineService).create(any(Machine.class));
    }

    @Test
    public void deleteMachineTest() {
        when(machineService.findById(testingMachine1.getId())).thenReturn(testingMachine1);
        machineFacade.deleteMachine(testingMachine1.getId());
        verify(machineService).findById(testingMachine1.getId());
        verify(machineService).remove(testingMachine1);
    }
}
