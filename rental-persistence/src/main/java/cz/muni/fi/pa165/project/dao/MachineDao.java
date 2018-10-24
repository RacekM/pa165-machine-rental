package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Machine;

import java.util.List;

/**
 * Dao interface for machine entity
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */
public interface MachineDao {
    void create(Machine machine);

    void update(Machine machine);

    Machine findById(Long id);

    List<Machine> findAll();

    void delete(Machine machine);
}
