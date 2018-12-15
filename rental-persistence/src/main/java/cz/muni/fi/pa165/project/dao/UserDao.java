package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.User;

import java.util.List;


/**
 * Interface of DAO for User entity providing basic CRUD operations
 * @author Juraj Vandor
 */
public interface UserDao {

    /**
     * Persists user in data storage
     *
     * @param user user to be persisted
     */
    void create(User user);

    /**
     * Finds customer from data storage by id
     *
     * @param id customers id
     * @return customer entity with given id, null if no such customer exists
     */
    User findById(Long id);

    /**
     * Finds all customers
     *
     * @return list of all customers in data storage
     */
    List<User> findAll();

    /**
     * Removes user from data storage
     *
     * @param user user to be removed
     */
    void delete(User user);

    /**
     * Updates user in data storage
     *
     * @param user updated user entity
     */
    void update(User user);

    User findByUsername (String username);

}
