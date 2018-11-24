package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.dao.RentalDao;
import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.entity.Machine;
import cz.muni.fi.pa165.project.entity.Rental;
import cz.muni.fi.pa165.project.entity.Revision;
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
    public List<Rental> findByCustomer(Customer customer) {
        return rentalDao.findByCustomer(customer);
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
    public Map<Rental, Revision> rentedMachinesAndTheyrLastRevisionByCustomer(Customer customer) {
        List<Rental> rentals = rentalDao.findByCustomer(customer);
        Map<Rental, Revision> res= new HashMap<>();
        for (Rental r : rentals){
            if (r.getReturnDate().isAfter(LocalDateTime.now()))
                res.put(r, revisionService.getLastMachineRevision(r.getMachine()));
        }
        return res;
    }
}
