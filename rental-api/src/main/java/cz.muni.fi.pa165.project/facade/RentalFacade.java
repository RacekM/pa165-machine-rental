package cz.muni.fi.pa165.project.facade;

import cz.muni.fi.pa165.project.dto.*;

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
    List<RentalDTO> getRentalsByUser(Long customerId);

    List<RentalDTO> getRentalsByMachine(Long machineId);

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
     * Changes feedback of rental.
     *
     * @param rentalChangeFeedbackDTO rental and feedback
     */
    void changeRentalFeedback(RentalChangeFeedbackDTO rentalChangeFeedbackDTO);


    /**
     * Checks if rental can be created from the timeline point of view.
     *
     * @param rentalCreateDTO rental to validate
     * @return true if rentalCreateDTO can be created, false otherwise
     */
    boolean isValidRental(RentalCreateDTO rentalCreateDTO);

    /**
     * Finds last revision of active rentals for Customer
     *
     * @param customerDTO whose rentals we are considering
     * @return map of rental and last revision of rented machine
     */
    Map<RentalDTO, RevisionDTO> activeRentalsWithLastRevisionByCustomer(UserDTO customerDTO);

}
