package cz.muni.fi.pa165.project.facade;

import cz.muni.fi.pa165.project.dto.RentalCreateDTO;
import cz.muni.fi.pa165.project.dto.RentalDTO;

import java.util.List;

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
    Long create(RentalCreateDTO rentalCreateDTO);

    /**
     * Finds rental by id number.
     *
     * @param rentalId id number of the rental to find
     * @return Found rental if data storage contains it, null otherwise.
     */
    RentalDTO findById(Long rentalId);

    /**
     * Gets all rentals made by one customer.
     *
     * @param customerId id of customer whose rentals to find
     * @return List of rentals associated with customer.
     */
    List<RentalDTO> findByCustomer(Long customerId);

    /**
     * Gets all rentals.
     *
     * @return All rentals.
     */
    List<RentalDTO> findAll();

    /**
     * Deletes rental.
     *
     * @param rentalId id of rental to remove
     */
    void remove(Long rentalId);

}