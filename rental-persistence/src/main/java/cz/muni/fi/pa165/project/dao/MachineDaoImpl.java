package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Machine;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


/**
 * Implementation of MachineDao
 *
 * @author Juraj Vandor
 */
@Repository
public class MachineDaoImpl implements MachineDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates new machine
     *
     * @param machine machine to be created
     */
    @Override
    public void create(Machine machine) {
        entityManager.persist(machine);
    }

    /**
     * Updates given machine
     *
     * @param machine machine to be updated
     */
    @Override
    public void update(Machine machine) {
        entityManager.merge(machine);
    }

    /**
     * Finds machine by its ID
     *
     * @param id id of the machine
     * @return machine
     */
    @Override
    public Machine findById(Long id) {
        return entityManager.find(Machine.class, id);
    }

    /**
     * Finds all machines
     *
     * @return List of all machines
     */
    @Override
    public List<Machine> findAll() {
        return entityManager.createQuery("select m from Machine m", Machine.class).getResultList();
    }

    /**
     * Deletes given machine
     *
     * @param machine machine to be deleted
     */
    @Override
    public void delete(Machine machine) {
        entityManager.remove(entityManager.merge(machine));
    }
}
