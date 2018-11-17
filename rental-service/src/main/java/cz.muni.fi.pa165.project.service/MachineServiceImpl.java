package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.dao.MachineDao;
import cz.muni.fi.pa165.project.entity.Machine;

import javax.inject.Inject;
import java.util.List;


/**
 * Implementation of the {@link MachineService}.
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */

public class MachineServiceImpl implements MachineService {

    @Inject
    private MachineDao machineDao;

    @Override
    public Machine findById(Long machineId) {
        return machineDao.findById(machineId);
    }

    @Override
    public List<Machine> findAll() {
        return machineDao.findAll();
    }

    @Override
    public void create(Machine machine) {
        machineDao.create(machine);
    }

    @Override
    public void remove(Machine machine) {
        machineDao.delete(machine);
    }

    @Override
    public void update(Machine machine) {
        machineDao.update(machine);
    }
}
