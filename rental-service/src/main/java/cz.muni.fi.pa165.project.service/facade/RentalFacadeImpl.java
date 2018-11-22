package cz.muni.fi.pa165.project.service.facade;

import cz.muni.fi.pa165.project.dto.RentalCreateDTO;
import cz.muni.fi.pa165.project.dto.RentalDTO;
import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.entity.Rental;
import cz.muni.fi.pa165.project.facade.RentalFacade;
import cz.muni.fi.pa165.project.service.BeanMappingService;
import cz.muni.fi.pa165.project.service.CustomerService;
import cz.muni.fi.pa165.project.service.MachineService;
import cz.muni.fi.pa165.project.service.RentalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
    private CustomerService customerService;

    @Inject
    private MachineService machineService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long create(RentalCreateDTO rentalCreateDTO) {
        Rental rental = new Rental();
        rental.setDateOfRental(rentalCreateDTO.getDateOfRental());
        rental.setReturnDate(rentalCreateDTO.getReturnDate());
        rental.setFeedback(rentalCreateDTO.getFeedback());
        rental.setMachine(machineService.findById(rentalCreateDTO.getMachine().getId()));
        rental.setCustomer(customerService.findById(rentalCreateDTO.getCustomer().getId()));
        rentalService.create(rental);

        return rental.getId();
    }

    @Override
    public RentalDTO findById(Long rentalId) {
        Rental rental = rentalService.findById(rentalId);
        return rental == null ? null : beanMappingService.mapTo(rental, RentalDTO.class);
    }

    @Override
    public List<RentalDTO> findByCustomer(Long customerId) {
        Customer customer = customerService.findById(customerId);
        List<RentalDTO> rentalDTOs = new ArrayList<>();
        if (customer != null) {
            rentalDTOs = beanMappingService.mapTo(rentalService.findByCustomer(customer), RentalDTO.class);
        }
        return rentalDTOs;
    }

    @Override
    public List<RentalDTO> findAll() {
        return beanMappingService.mapTo(rentalService.findAll(), RentalDTO.class);
    }

    @Override
    public void remove(Long rentalId) {
        Rental rental = rentalService.findById(rentalId);
        if (rental != null) {
            rentalService.remove(rental);
        }
    }

}
