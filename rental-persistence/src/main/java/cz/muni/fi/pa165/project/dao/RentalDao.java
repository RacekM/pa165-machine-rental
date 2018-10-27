package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.entity.Rental;

import java.util.List;

/**
 * DAO interface for entity Rental.
 *
 * @author Adam Vaňko (445310@mail.muni.cz)
 */
public interface RentalDao {

    /**
     * Persist given object in data storage.
     *
     * @param rental object to persist
     */
    void create(Rental rental);

    /**
     * Update given object in data storage.
     *
     * @param rental object to update
     */
    void update(Rental rental);

    /**
     * Finds object by id number.
     *
     * @param id id number of the object
     * @return Found object if data storage contains it, null otherwise.
     */
    Rental findById(Long id);

    /**
     * Finds all rentals made by one customer.
     *
     * @param customer customer whose rentals to find
     * @return List of rentals associated with customer.
     */
    List<Rental> findByCustomer(Customer customer);

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
