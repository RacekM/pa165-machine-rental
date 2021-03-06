package cz.muni.fi.pa165.project.service.facade;

import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.project.dto.RevisionCreateDTO;
import cz.muni.fi.pa165.project.dto.RevisionDTO;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Revision;
import cz.muni.fi.pa165.project.facade.RevisionFacade;
import cz.muni.fi.pa165.project.service.BeanMappingService;
import cz.muni.fi.pa165.project.service.MachineService;
import cz.muni.fi.pa165.project.service.RevisionService;
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
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * @author Juraj Vandor
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RevisionFacadeTest extends AbstractTestNGSpringContextTests {
    @Mock
    private RevisionService revisionService;
    @Mock
    private MachineService machineService;

    @Spy
    @Inject
    private BeanMappingService beanMappingService;

    @InjectMocks
    private RevisionFacade revisionFacade = new RevisionFacadeImpl();

    private Machine machine;
    private Revision revision1;
    private Revision revision2;
    private Revision revision3;

    @BeforeMethod
    public void testInit() {
        MockitoAnnotations.initMocks(this);
        machine = new Machine("Drill");
        LocalDateTime time1 = LocalDateTime.of(2018, 10, 1, 0, 0);
        revision1 = new Revision(true, time1, machine);
        LocalDateTime time2 = LocalDateTime.of(2018, 10, 8, 0, 0);
        revision2 = new Revision(true, time2, machine);
        LocalDateTime time3 = LocalDateTime.of(2018, 11, 8, 0, 0);
        revision3 = new Revision(true, time3, new Machine("bulldozer"));
    }

    @Test
    public void getByIdTest() {
        when(revisionService.findById(revision1.getId())).thenReturn(revision1);
        RevisionDTO revisionDTO = revisionFacade.getRevisionById(revision1.getId());
        verify(revisionService).findById(revision1.getId());
        assertEquals(revision1, beanMappingService.mapTo(revisionDTO, Revision.class));
    }

    @Test
    public void getAllTest() {
        List<Revision> array = Arrays.asList(revision1, revision2);
        when(revisionService.findAll()).thenReturn(array);
        List<RevisionDTO> resultDTO = revisionFacade.getAllRevisions();
        verify(revisionService).findAll();
        List<Revision> result = beanMappingService.mapTo(resultDTO, Revision.class);
        assertEquals(result, array);
    }

    @Test
    public void createTest() {
        RevisionCreateDTO revisionCreateDTO = new RevisionCreateDTO();
        revisionCreateDTO.setMachine(machine.getId());
        LocalDateTime time = LocalDateTime.of(2018, 10, 12, 0, 0);
        revisionCreateDTO.setDate(time);
        machineService.create(machine);
        revisionFacade.createRevision(revisionCreateDTO);
        verify(revisionService).create(any(Revision.class));
    }

    @Test
    public void deleteTest() {
        when(revisionService.findById(revision1.getId())).thenReturn(revision1);
        revisionFacade.deleteRevision(revision1.getId());
        verify(revisionService).findById(revision1.getId());
        verify(revisionService).remove(revision1);
    }

    @Test
    public void findMachineRevisionsTest(){
        List<Revision> array = Arrays.asList(revision1, revision2);
        when(revisionService.findMachineRevisions(machine)).thenReturn(array);
        List<RevisionDTO> resultDTO = revisionFacade.getRevisionsOfMachine(beanMappingService.mapTo(machine, MachineDTO.class));
        verify(revisionService).findMachineRevisions(machine);
        List<Revision> result = beanMappingService.mapTo(resultDTO, Revision.class);
        assertEquals(result, array);
    }

    @Test
    public void getLastMachineRevisionTest() {
        when(revisionService.getLastMachineRevision(machine)).thenReturn(revision2);
        RevisionDTO revisionDTO = revisionFacade.getLastMachineRevision(beanMappingService.mapTo(machine, MachineDTO.class));
        verify(revisionService).getLastMachineRevision(machine);
        assertEquals(revision2, beanMappingService.mapTo(revisionDTO, Revision.class));
    }
}
