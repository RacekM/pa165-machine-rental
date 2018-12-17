package cz.muni.fi.pa165.project.service.facade;

import cz.muni.fi.pa165.project.dto.*;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Rental;
import cz.muni.fi.pa165.project.entity.Revision;
import cz.muni.fi.pa165.project.entity.User;
import cz.muni.fi.pa165.project.facade.RentalFacade;
import cz.muni.fi.pa165.project.service.BeanMappingService;
import cz.muni.fi.pa165.project.service.MachineService;
import cz.muni.fi.pa165.project.service.RentalService;
import cz.muni.fi.pa165.project.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link RentalFacade}
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
@Service
@Transactional
public class RentalFacadeImpl implements RentalFacade {

    @Inject
    private RentalService rentalService;

    @Inject
    private UserService userService;

    @Inject
    private MachineService machineService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createRental(RentalCreateDTO rentalCreateDTO) {
        //Rental rental = beanMappingService.mapTo(rentalCreateDTO, Rental.class);
        Rental rental = new Rental(rentalCreateDTO.getDateOfRental(), rentalCreateDTO.getReturnDate(), rentalCreateDTO.getFeedback()
        , machineService.findById(rentalCreateDTO.getMachine()), userService.findById(rentalCreateDTO.getUser()));
        rental.setMachine(machineService.findById(rentalCreateDTO.getMachine()));
        rental.setUser(userService.findById(rentalCreateDTO.getUser()));

        if (!rentalService.isValid(rental)) {
            throw new IllegalArgumentException("New rental is invalid.");
        }

        rentalService.create(rental);

        return rental.getId();
    }

    @Override
    public RentalDTO getRentalById(Long rentalId) {
        Rental rental = rentalService.findById(rentalId);
        return (rental == null ? null : beanMappingService.mapTo(rental, RentalDTO.class));
    }

    @Override
    public List<RentalDTO> getRentalsByUser(Long customerId) {
        User user = userService.findById(customerId);
        List<RentalDTO> rentalDTOs = new ArrayList<>();
        if (user != null) {
            rentalDTOs = beanMappingService.mapTo(rentalService.findByCustomer(user), RentalDTO.class);
        }
        return rentalDTOs;
    }

    @Override
    public List<RentalDTO> getRentalsByMachine(Long machineId) {
        Machine machine = machineService.findById(machineId);
        List<RentalDTO> rentalDTOs = new ArrayList<>();
        if (machine != null) {
            rentalDTOs = beanMappingService.mapTo(rentalService.findByMachine(machine), RentalDTO.class);
        }
        return rentalDTOs;
    }

    @Override
    public List<RentalDTO> getAllRentals() {
        return beanMappingService.mapTo(rentalService.findAll(), RentalDTO.class);
    }

    @Override
    public void deleteRental(Long rentalId) {
        Rental rental = rentalService.findById(rentalId);
        if (rental != null) {
            rentalService.remove(rental);
        }
    }

    @Override
    public void changeRentalFeedback(RentalChangeFeedbackDTO rentalChangeFeedbackDTO) {
        Rental rental = beanMappingService.mapTo(rentalChangeFeedbackDTO.getRental(), Rental.class);
        rentalService.changeFeedback(rental, rentalChangeFeedbackDTO.getFeedback());
    }

    @Override
    public boolean isValidRental(RentalCreateDTO rentalCreateDTO) {
        return rentalService.isValid(beanMappingService.mapTo(rentalCreateDTO, Rental.class));
    }

    @Override
    public Map<RentalDTO, RevisionDTO> activeRentalsWithLastRevisionByCustomer(UserDTO customerDTO){
        Map<Rental, Revision> result = rentalService.activeRentalsWithLastRevisionByCustomer(
                beanMappingService.mapTo(customerDTO, User.class));
        return beanMappingService.mapTo(result, RentalDTO.class, RevisionDTO.class);
    }

}
