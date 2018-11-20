package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.entity.Machine;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Machine} entity.
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */

public interface MachineService {

    Machine findById(Long machineId);

    List<Machine> findAll();

    void create(Machine machine);

    void remove(Machine machine);

    void update(Machine machine);

}
