package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.entity.Rental;
import cz.muni.fi.pa165.project.entity.Revision;

import java.util.List;
import java.util.Map;

/**
 * An interface that defines a service access to the {@link Rental} entity.
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
public interface RentalService {

    /**
     * Creates new rental.
     *
     * @param rental rental to create
     */
    void create(Rental rental);

    /**
     * Updates rental.
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
     * Finds all rentals made by one customer.
     *
     * @param customer customer whose rentals to find
     * @return List of rentals associated with customer.
     */
    List<Rental> findByCustomer(Customer customer);

    /**
     * Finds all rentals.
     *
     * @return All rentals from data storage.
     */
    List<Rental> findAll();

    /**
     * Deletes rental.
     *
     * @param rental rental to remove
     */
    void remove(Rental rental);

    /**
     * Changes feedback of rental.
     *
     * @param rental      rental, whose feedback will be changed
     * @param newFeedback new feedback
     */
    void changeFeedback(Rental rental, String newFeedback);


    /**
     * Checks if rental can be created from the timeline point of view.
     *
     * @param rental rental to validate
     * @return true if rental can be created, false otherwise
     */
    boolean isValid(Rental rental);

    /**
     * Finds last revision of active rentals for Customer
     *
     * @param customer whose rentals we are considering
     * @return map of rental and last revision of rented machine
     */
    Map<Rental, Revision> activeRentalsWithLastRevisionByCustomer(Customer customer);

}
