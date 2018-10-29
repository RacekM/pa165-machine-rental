package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Machine;

import java.util.List;

/**
 * DAO interface for entity {@link Machine}.
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */
public interface MachineDao {
    /**
     * Persist machine in data storage.
     *
     * @param machine machine to persist
     */
    void create(Machine machine);

    /**
     * Update machine in data storage.
     *
     * @param machine machine to update
     */
    void update(Machine machine);

    /**
     * Finds machine by id.
     *
     * @param id id number of the rental
     * @return Found machine if data storage contains it, null otherwise.
     */
    Machine findById(Long id);

    /**
     * Finds all machines
     *
     * @return List of all machines
     */
    List<Machine> findAll();

    /**
     * Deletes machine from data storage.
     *
     * @param machine machine to remove
     */
    void delete(Machine machine);
}
