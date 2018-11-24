package cz.muni.fi.pa165.project.facade;

import cz.muni.fi.pa165.project.dto.CustomerDTO;
import cz.muni.fi.pa165.project.dto.RentalCreateDTO;
import cz.muni.fi.pa165.project.dto.RentalDTO;
import cz.muni.fi.pa165.project.dto.RevisionDTO;

import java.util.List;
import java.util.Map;

/**
 * RentalFacade interface
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
public interface RentalFacade {

    /**
     * Creates new rental.
     *
     * @param rentalCreateDTO new rental to create
     */
    Long createRental(RentalCreateDTO rentalCreateDTO);

    /**
     * Finds rental by id number.
     *
     * @param rentalId id number of the rental to find
     * @return Found rental if data storage contains it, null otherwise.
     */
    RentalDTO getRentalById(Long rentalId);

    /**
     * Gets all rentals made by one customer.
     *
     * @param customerId id of customer whose rentals to find
     * @return List of rentals associated with customer.
     */
    List<RentalDTO> getRentalsByCustomer(Long customerId);

    /**
     * Gets all rentals.
     *
     * @return All rentals.
     */
    List<RentalDTO> getAllRentals();

    /**
     * Deletes rental.
     *
     * @param rentalId id of rental to remove
     */
    void deleteRental(Long rentalId);

    /**
     * Finds last revision of active rentals for Customer
     *
     * @param customerDTO whose rentals we are considering
     * @return map of rental and last revision of rented machine
     */
    Map<RentalDTO, RevisionDTO> activeRentalsWithLastRevisionByCustomer(CustomerDTO customerDTO);

}
