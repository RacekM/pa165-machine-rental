package cz.muni.fi.pa165.project.sampledata;

import cz.muni.fi.pa165.project.dao.MachineDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

/**
 * Tests data loading.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@ContextConfiguration(classes = {RentalWithSampleDataConfiguration.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SampleDataLoadingFacadeTest extends AbstractTestNGSpringContextTests {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeTest.class);

    @Autowired
    public MachineDao machineDao;

    @Autowired
    public SampleDataLoadingFacade sampleDataLoadingFacade;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void createSampleData() throws IOException {
        log.debug("starting test");

        Assert.assertTrue(machineDao.findAll().size() > 0, "no machines");
/*
        User admin = userService.getAllUsers().stream().filter(userService::isAdmin).findFirst().get();
        Assert.assertEquals(true, userService.authenticate(admin,"admin"));
*/
    }

}