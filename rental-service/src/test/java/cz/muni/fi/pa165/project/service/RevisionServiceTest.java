package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.dao.RevisionDao;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Revision;
import cz.muni.fi.pa165.project.service.configuration.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNull;

/**
 * @author Juraj Vandor
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RevisionServiceTest extends AbstractTestNGSpringContextTests{

    @Mock
    private RevisionDao revisionDao;

    @Autowired
    @InjectMocks
    private RevisionService revisionService;

    private Revision revision;
    private Revision revision2;
    private Revision revision3;

    @BeforeMethod
    public void testInit() {
        MockitoAnnotations.initMocks(this);
        Machine machine = new Machine("Drill");
        LocalDateTime time = LocalDateTime.of(2018, 5, 5, 0, 0);
        revision = new Revision(true, time, machine);
        LocalDateTime time2 = LocalDateTime.of(2018, 5, 6, 0, 0);
        revision2 = new Revision(true, time2, machine);
        Machine machine2 = new Machine("bulldozer");
        LocalDateTime time3 = LocalDateTime.of(2018, 5, 7, 0, 0);
        revision3 = new Revision(true, time3, machine2);
    }

    @Test
    public void getByIdTest() {
        long id = 1;
        when(revisionDao.findById(id)).thenReturn(revision);
        Revision revision = revisionService.findById(id);
        verify(revisionDao).findById(id);
        assertEquals(revision, this.revision);
    }

    @Test
    public void getByIdInvalidIdTest() {
        long id = -1;
        when(revisionDao.findById(id)).thenReturn(null);
        assertNull(revisionDao.findById(id));
        verify(revisionDao).findById(id);
    }

    @Test
    public void getAllTest() {
        List<Revision> list = Collections.singletonList(revision);
        when(revisionDao.findAll()).thenReturn(Collections.unmodifiableList(list));
        List<Revision> result = revisionService.findAll();
        verify(revisionDao).findAll();
        assertEquals(result, list);
    }

    @Test
    public void getAllEmptyTest() {
        List<Revision> list = Collections.EMPTY_LIST;
        when(revisionDao.findAll()).thenReturn(Collections.unmodifiableList(list));
        List<Revision> result = revisionService.findAll();
        verify(revisionDao).findAll();
        assertEquals(list, result);
    }

    @Test
    public void createValidTest() {
        revisionService.create(revision);
        verify(revisionDao).create(revision);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createInvalidTest() {
        revision.setMachine(null);
        doThrow(new DataIntegrityViolationException("")).when(revisionDao).create(revision);
        revisionService.create(revision);
    }

    @Test
    public void updateValidTest() {
        revision.setMachine(new Machine("UltraDrill"));
        doNothing().when(revisionDao).update(revision);
        revisionService.update(revision);
        verify(revisionDao).update(revision);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void updateInvalidTest() {
        revision.setMachine(null);
        doThrow(new DataIntegrityViolationException(""))
                .when(revisionDao).update(revision);
        revisionService.update(revision);
    }

    @Test
    public void deleteTest() {
        doNothing().when(revisionDao).delete(revision);
        revisionService.remove(revision);
        verify(revisionDao).delete(revision);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void deleteNullTest() {
        doThrow(new InvalidDataAccessApiUsageException(""))
                .when(revisionDao).delete(null);
        revisionService.remove(null);
    }

    @Test
    public void findMachineRevisionsTest(){
        List<Revision> list = Arrays.asList(revision, revision2);
        when(revisionDao.findByMachine(revision.getMachine())).thenReturn(Collections.unmodifiableList(list));
        List<Revision> result = revisionService.findMachineRevisions(revision.getMachine());
        verify(revisionDao).findByMachine(revision.getMachine());
        assertEquals(result, list);
    }

    @Test
    public void getLastMachineRevisionTest(){
        when(revisionDao.findLastRevisionByMachine(revision.getMachine())).thenReturn(revision2);
        Revision revision = revisionService.getLastMachineRevision(this.revision.getMachine());
        verify(revisionDao).findLastRevisionByMachine(this.revision.getMachine());
        assertEquals(revision, revision2);
    }
}