package cz.muni.fi.pa165.project.sampledata;

import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.service.MachineService;
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

    private static Date daysBeforeNow(int days) {
        return Date.from(ZonedDateTime.now().minusDays(days).toInstant());
    }

    private static Date toDate(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public void loadData() throws IOException {
        Machine machineHammer = Machine("Hammer");
        Machine machineShovel = Machine("Shovel");
        Machine machineDrill = Machine("Drill");
        Machine machineWaterPump = Machine("Water Pump");
        Machine machineExcavator = Machine("Excavator");
        log.info("Loaded rental machines");
    }

    private Machine Machine(String name) {
        Machine m = new Machine();
        m.setName(name);
        machineService.create(m);
        return m;
    }

/*
    private User user(String password, String givenName, String surname, String email, String phone, Date joined, String address) {
        User u = new User();
        u.setGivenName(givenName);
        u.setSurname(surname);
        u.setEmail(email);
        u.setPhone(phone);
        u.setAddress(address);
        u.setJoinedDate(joined);
        if(password.equals("admin")) u.setAdmin(true);
        userService.registerUser(u, password);
        return u;
    }
*/
}
