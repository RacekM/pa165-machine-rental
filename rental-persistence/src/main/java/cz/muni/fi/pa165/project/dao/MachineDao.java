package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Machine;

import java.util.List;

public interface MachineDao {
    void create(Machine machine);
    void update(Machine machine);
    Machine findById(Long id);
    List<Machine> findAll();
    void delete(Machine machine);
}
