package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.dao.RentalDao;
import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.entity.Rental;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the {@link RentalService}.
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
@Service
public class RentalServiceImpl implements RentalService {

    @Inject
    private RentalDao rentalDao;

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
    public void changeFeedback(Rental rental, String newFeedback) {
        rental.setFeedback(newFeedback);
        rentalDao.update(rental);
    }

}
