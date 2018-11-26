package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.entity.Machine;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Machine} entity.
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */

public interface MachineService {

    /**
     * Finds machine by id number.
     *
     * @param machineId unique identifier of machine
     * @return Found machine if data storage contains it, null otherwise.
     */
    Machine findById(Long machineId);

    /**
     * Finds all machines.
     *
     * @return All machines from data storage.
     */
    List<Machine> findAll();

    /**
     * Creates new machine.
     *
     * @param machine machine to create
     */
    void create(Machine machine);

    /**
     * Deletes machine.
     *
     * @param machine machine to remove
     */
    void remove(Machine machine);

    /**
     * Updates machine.
     *
     * @param machine machine to update
     */
    void update(Machine machine);

}
