package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.dao.RentalDao;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Rental;
import cz.muni.fi.pa165.project.entity.Revision;
import cz.muni.fi.pa165.project.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link RentalService}.
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
@Service
public class RentalServiceImpl implements RentalService {

    @Inject
    private RentalDao rentalDao;

    @Inject
    private RevisionService revisionService;

    @Override
    public void create(Rental rental) {
        rentalDao.create(rental);
    }

    @Override
    public void update(Rental rental) {
        rentalDao.update(rental);
    }

    @Override
    public Rental findById(Long id) {
        return rentalDao.findById(id);
    }

    @Override
    public List<Rental> findByCustomer(User user) {
        return rentalDao.findByCustomer(user);
    }

    @Override
    public List<Rental> findByMachine(Machine machine) {
        return rentalDao.findByMachine(machine);
    }

    @Override
    public List<Rental> findAll() {
        return rentalDao.findAll();
    }

    @Override
    public void remove(Rental rental) {
        rentalDao.delete(rental);
    }

    @Override
    public void changeNote(Rental rental, String newNote) {
        rental.setNote(newNote);
        rentalDao.update(rental);
    }

    @Override
    public boolean isValid(Rental rental) {
        if (rental.getDateOfRental() == null || rental.getReturnDate() == null || rental.getMachine() == null) {
            return false;
        }

        if (!rental.getDateOfRental().isBefore(rental.getReturnDate())) {
            return false;
        }

        List<Rental> existingRentals = findAll();
        for (Rental existingRental : existingRentals) {
            if (!rental.getMachine().equals(existingRental.getMachine())) {
                continue;
            }

            boolean isBefore = existingRental.getReturnDate().toLocalDate().isBefore(rental.getDateOfRental().toLocalDate());
            boolean isAfter = existingRental.getDateOfRental().toLocalDate().isAfter(rental.getReturnDate().toLocalDate());

            if (!isBefore && !isAfter) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<Rental, Revision> activeRentalsWithLastRevisionByCustomer(User user){
        List<Rental> rentals = rentalDao.findByCustomer(user);
        Map<Rental, Revision> res= new HashMap<>();
        for (Rental r : rentals){
            if (r.getReturnDate().isAfter(LocalDateTime.now()))
                res.put(r, revisionService.getLastMachineRevision(r.getMachine()));
        }
        return res;
    }

}
