package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Machine;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


/**
 * Implementation of MachineDao using in-memory database
 *
 * @author Juraj Vandor
 */
@Repository
public class MachineDaoImpl implements MachineDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Machine machine) {
        entityManager.persist(machine);
    }

    @Override
    public void update(Machine machine) {
        entityManager.merge(machine);
    }

    @Override
    public Machine findById(Long id) {
        return entityManager.find(Machine.class, id);
    }

    @Override
    public List<Machine> findAll() {
        return entityManager.createQuery("select m from Machine m", Machine.class).getResultList();
    }

    @Override
    public void delete(Machine machine) {
        entityManager.remove(machine);
    }
}
