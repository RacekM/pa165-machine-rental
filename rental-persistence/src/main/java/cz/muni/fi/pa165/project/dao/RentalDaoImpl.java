package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Rental;
import cz.muni.fi.pa165.project.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of rental Dao
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */
@Repository
public class RentalDaoImpl implements RentalDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Rental rental) {
        entityManager.persist(rental);
    }

    @Override
    public void update(Rental rental) {
        entityManager.merge(rental);
    }

    @Override
    public Rental findById(Long id) {
        return entityManager.find(Rental.class, id);
    }

    @Override
    public List<Rental> findByUser(User user) {
        TypedQuery<Rental> query = entityManager.createQuery(
                "SELECT r FROM Rental r WHERE r.user = :userId", Rental.class);

        query.setParameter("userId", user);
        return query.getResultList();
    }

    @Override
    public List<Rental> findByMachine(Machine machine) {
        TypedQuery<Rental> query = entityManager.createQuery(
                "SELECT r FROM Rental r WHERE r.machine = :machineId", Rental.class);
        query.setParameter("machineId", machine);
        return query.getResultList();
    }

    @Override
    public List<Rental> findAll() {
        return entityManager.createQuery("SELECT r FROM Rental r", Rental.class).getResultList();
    }

    @Override
    public void delete(Rental rental) {
        entityManager.remove(rental);
    }
}
