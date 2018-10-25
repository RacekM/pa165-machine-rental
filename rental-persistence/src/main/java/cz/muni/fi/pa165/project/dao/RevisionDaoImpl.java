package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Revision;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Adam Vaňko (445310@mail.muni.cz)
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

}