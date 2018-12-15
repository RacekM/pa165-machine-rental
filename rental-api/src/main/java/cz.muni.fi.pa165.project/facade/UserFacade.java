package cz.muni.fi.pa165.project.facade;

import cz.muni.fi.pa165.project.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.project.dto.UserDTO;
import cz.muni.fi.pa165.project.enums.UserType;

import java.util.List;

/**
 * UserFacade Interface
 *
 * @author Martin Sisak, 445384
 */
public interface UserFacade {

    /**
     * Finds customer by his id
     *
     * @param customerId Identifier for customer
     * @return Finds customer if data storage contains it, null otherwise
     */
    UserDTO getUserById(Long customerId);

    /**
     * Finds all customers
     *
     * @return List of all customers
     */
    List<UserDTO> getAllUsers();

    /**
     * Creates new customer
     *
     * @param userDTO customer to be registered
     * @param plainPassword password
     */
    Long registerUser(UserDTO userDTO, String plainPassword);

    /**
     * Deletes customer
     *
     * @param userId ID of user to be deleted
     */
    void deleteUser(Long userId);

    /**
     * Updates customer
     *
     * @param userDTO customer for update
     */
    void updateUser(UserDTO userDTO);

    /**
     * Gets all customers with specified UserType
     *
     * @param userType Type of customer
     * @return List of all customers with specified UserType
     */
    List<UserDTO> getAllByUserType(UserType userType);

    /**
     * Find user by username.
     *
     * @param username Username to search by.
     * @return         User with the given username (if any).
     */
    public UserDTO findUserByUsername (String username);


    /**
     * Try to authenticate a user. Return true only if the hashed password matches the records.
     */
    boolean authenticate(UserAuthenticateDTO u);

    /**
     *
     * @param userDTO
     * @return
     */
    boolean isAdmin(UserDTO userDTO);

}
