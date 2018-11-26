package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.PersistenceApplicationContext;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Revision;
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
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Matus Racek (mat.racek@gmail.com)
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class RevisionDaoImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public RevisionDao revisionDao;

    @Autowired
    public MachineDao machineDao;

    private LocalDateTime date1;
    private LocalDateTime date2;
    private LocalDateTime date3;
    private Machine excavator;
    private Machine bulldozer;
    private Revision revision1;
    private Revision revision2;
    private Revision revision3;

    @BeforeMethod
    public void createRevisions() {
        excavator = new Machine("excavator");
        machineDao.create(excavator);
        bulldozer = new Machine("bulldozer");
        machineDao.create(bulldozer);
        date1 = LocalDateTime.of(2018 ,1,1, 0, 0);
        date2 = LocalDateTime.of(2018, 7, 1, 0, 0);
        date3 = LocalDateTime.of(2018, 10, 1, 0, 0);

        revision1 = new Revision(false, date1, bulldozer);
        revision2 = new Revision(true, date2, bulldozer);
        revision3 = new Revision(true, date3, excavator);

        revisionDao.create(revision1);
        revisionDao.create(revision2);
        revisionDao.create(revision3);
    }

    @Test
    public void createValidRevisionTest() {
        Revision revision = new Revision(true, date1, bulldozer);
        revisionDao.create(revision);

        Revision persistedRevision = entityManager.find(Revision.class, revision.getId());
        Assert.assertNotNull(revision);
        Assert.assertEquals(persistedRevision, revision);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createRevisionWithNullMachineTest() {
        Revision revision = new Revision(true, date1, null);
        revisionDao.create(revision);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createRevisionWithNullDateTest() {
        Revision revision = new Revision(true, null, bulldozer);
        revisionDao.create(revision);
    }

    @Test
    public void findNonExistingMachineTest() {
        long notExistingId = -1L;
        Assert.assertNull(revisionDao.findById(notExistingId));
    }

    @Test
    public void findMachineByIdTest() {
        Revision found = revisionDao.findById(revision1.getId());

        Assert.assertEquals(found.getDate(), date1);
        Assert.assertEquals(found.getMachine(), bulldozer);
        Assert.assertFalse(found.getResult());
    }

    @Test
    public void findAllRevisionsTest() {
        List<Revision> revisions = revisionDao.findAll();
        Assert.assertEquals(revisions.size(), 3);
        Assert.assertTrue(revisions.contains(revision1));
        Assert.assertTrue(revisions.contains(revision2));
        Assert.assertTrue(revisions.contains(revision3));
    }

    @Test
    public void deleteRevisionTest() {
        Assert.assertNotNull(entityManager.find(Revision.class, revision1.getId()));
        revisionDao.delete(revision1);
        Assert.assertNull(entityManager.find(Revision.class, revision1.getId()));
    }

    @Test
    public void deleteNonExistingRevisionTest() {
        Revision notExistingRevision = new Revision(false, date2, bulldozer);
        revisionDao.delete(notExistingRevision);
        Assert.assertEquals(entityManager.createQuery("SELECT r FROM Revision r", Revision.class).getResultList().size(), 3);
    }

    @Test
    public void updateRevisionTest() {
        revision1.setResult(true);
        revisionDao.update(revision1);

        Revision r = entityManager.find(Revision.class, revision1.getId());
        Assert.assertNotNull(r);
        Assert.assertEquals(r, revision1);
    }

    @Test
    public void findByMachineTest() {
        List<Revision> revisions = revisionDao.findByMachine(bulldozer);
        Assert.assertEquals(revisions.size(), 2);
    }

    @Test
    public void findLastRevisionByMachineTest(){
        Revision revision = revisionDao.findLastRevisionByMachine(bulldozer);
        Assert.assertEquals(revision,revision2);
    }
}
