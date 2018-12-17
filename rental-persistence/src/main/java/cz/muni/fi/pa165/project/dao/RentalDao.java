package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Rental;
import cz.muni.fi.pa165.project.entity.User;

import java.util.List;

/**
 * DAO interface for entity {@link Rental}.
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
public interface RentalDao {

    /**
     * Persist rental in data storage.
     *
     * @param rental rental to persist
     */
    void create(Rental rental);

    /**
     * Update rental in data storage.
     *
     * @param rental rental to update
     */
    void update(Rental rental);

    /**
     * Finds rental by id number.
     *
     * @param id id number of the rental
     * @return Found rental if data storage contains it, null otherwise.
     */
    Rental findById(Long id);

    /**
     * Finds all rentals made by one user.
     *
     * @param user user whose rentals to find
     * @return List of rentals associated with user.
     */
    List<Rental> findByCustomer(User user);


    List<Rental> findByMachine(Machine machine);

    /**
     * Finds all rentals in data storage.
     *
     * @return All rentals from data storage.
     */
    List<Rental> findAll();

    /**
     * Deletes rental from data storage.
     *
     * @param rental rental to remove
     */
    void delete(Rental rental);

}
