package cz.muni.fi.pa165.project.sampledata;

import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.User;
import cz.muni.fi.pa165.project.enums.UserType;
import cz.muni.fi.pa165.project.service.MachineService;
import cz.muni.fi.pa165.project.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Loads some sample data to populate the eshop database.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@Component
@Transactional //transactions are handled on facade layer
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private MachineService machineService;

    @Autowired
    private UserService userService;

    private static Date daysBeforeNow(int days) {
        return Date.from(ZonedDateTime.now().minusDays(days).toInstant());
    }

    private static Date toDate(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public void loadData() throws IOException {
        Machine machineHammer = machine("Hammer");
        Machine machineShovel = machine("Shovel");
        Machine machineDrill = machine("Drill");
        Machine machineWaterPump = machine("Water Pump");
        Machine machineExcavator = machine("Excavator");
        log.info("Loaded rental machines");

        User admin = user("Fero", "admin", "admin", UserType.ADMIN);
        User customer1 = user("Jano", "user1", "user1", UserType.INDIVIDUAL);
        User customer2 = user("Jozo", "user2", "user2", UserType.LEGAL_PERSON);

    }

    private Machine machine(String name) {
        Machine m = new Machine();
        m.setName(name);
        machineService.create(m);
        return m;
    }


    private User user(String name, String username, String password, UserType userType) {
        User u = new User();
        u.setPasswordHash(password);
        u.setName(name);
        u.setUsername(username);
        u.setUserType(userType);
        userService.create(u);
        return u;
    }
}
