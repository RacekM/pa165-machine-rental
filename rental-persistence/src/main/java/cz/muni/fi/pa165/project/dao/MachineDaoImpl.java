package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Machine;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


/**
 * Implementation of interface MachineDao
 * @author Juraj Vandor
 */
@Repository
public class MachineDaoImpl implements MachineDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * persists entity machine
     * @param machine object to persist
     */
    @Override
    public void create(Machine machine) {
        entityManager.persist(machine);
    }

    /**
     * updates entity in DB (based on ID)
     * @param machine object to update
     */
    @Override
    public void update(Machine machine) {
        entityManager.merge(machine);
    }

    /**
     * @param id id of machine to be found
     * @return object of class machine with given id
     */
    @Override
    public Machine findById(Long id) {
        return entityManager.find(Machine.class, id);
    }

    /**
     * @return list of all machines in DB
     */
    @Override
    public List<Machine> findAll() {
        return entityManager.createQuery("select m from Machine m", Machine.class).getResultList();
    }

    /**
     * deletes given machine from database
     * @param machine object to be deleted
     */
    @Override
    public void delete(Machine machine) {
        entityManager.remove(entityManager.merge(machine));
    }
}
