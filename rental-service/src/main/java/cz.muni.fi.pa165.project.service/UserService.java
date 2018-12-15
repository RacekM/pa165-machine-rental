package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.dto.UserDTO;
import cz.muni.fi.pa165.project.entity.User;
import cz.muni.fi.pa165.project.enums.UserType;

import java.util.List;

/**
 * Service Interface to the entity {@link User}
 *
 * @author Martin Sisak, 445384
 */
public interface UserService {

    /**
     *  Finds customer by his id
     *
     * @param customerId Identifier for customer
     * @return Found customer if data storage contains it, null otherwise
     */
    User findById(Long customerId);

    /**
     * Finds all customers
     *
     * @return List of all customers from data storage
     */
    List<User> findAll();

    /**
     * Creates new user
     *
     * @param user User to create
     */
    void create(User user);

    /**
     * Deletes user
     *
     * @param user User to delete
     */
    void remove(User user);

    /**
     * Updates user
     *
     * @param user User to update
     */
    void update(User user);

    /**
     * Finds all customers with specified UserType
     *
     * @param userType Type of customer
     * @return List of customers with specified UserType
     */
    List<User> getAllByCustomerType(UserType userType);


    void registerUser(User user, String plainPassword);

    boolean isAdmin(User user);

    boolean authenticate(User user, String password);

    User findByUsername(String username);
}
