package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Revision;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Implementation of RevisionDao interface with database as data storage.
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
@Repository
public class RevisionDaoImpl implements RevisionDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void create(Revision revision) {
        entityManager.persist(revision);
    }

    @Override
    public void update(Revision revision) {
        entityManager.merge(revision);
    }

    @Override
    public Revision findById(Long id) {
        return entityManager.find(Revision.class, id);
    }

    @Override
    public List<Revision> findAll() {
        return entityManager.createQuery("SELECT r FROM Revision r", Revision.class).getResultList();
    }

    @Override
    public void delete(Revision revision) {
        entityManager.remove(entityManager.merge(revision));
    }

    @Override
    public List<Revision> findByMachine(Machine machine) {
        return entityManager.createQuery("SELECT r FROM Revision r WHERE r.machine = :machine", Revision.class)
                .setParameter("machine", machine)
                .getResultList();
    }

    @Override
    public Revision findLastRevisionByMachine(Machine machine) {
        return entityManager.createQuery(
                "SELECT r FROM Revision r WHERE r.date = (SELECT MAX(r2.date) FROM Revision r2 WHERE r2.machine = :machine) and r.machine = :machine",
                Revision.class)
                .setParameter("machine", machine)
                .getSingleResult();
    }

}
