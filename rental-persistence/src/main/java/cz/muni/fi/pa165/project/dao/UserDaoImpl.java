package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of User Dao interface with database as data storage
 *
 * @author Martin Sisak (445384@mail.muni.cz)
 */

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUsername (String username) {
        try {
            User user =
                    entityManager.createQuery ("select u from Users u where u.username=:username", User.class)
                            .setParameter ("username", username)
                            .getSingleResult ();
            return user;
        }
        catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT c FROM Users c", User.class).getResultList();
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }
}
