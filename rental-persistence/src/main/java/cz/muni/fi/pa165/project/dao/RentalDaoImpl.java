package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Rental;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public List<Rental> findAll() {
        return entityManager.createQuery("select r from Rental r", Rental.class).getResultList();
    }

    @Override
    public void delete(Rental rental) {
        rental = entityManager.merge(rental);
        entityManager.remove(rental);
    }
}