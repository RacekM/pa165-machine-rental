package cz.muni.fi.pa165.project.service;

import cz.muni.fi.pa165.project.dao.RentalDao;
import cz.muni.fi.pa165.project.entity.Customer;
import cz.muni.fi.pa165.project.entity.Rental;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
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

    @Override
    public boolean isValid(Rental rental) {
        if (rental.getDateOfRental() == null || rental.getReturnDate() == null) {
            return false;
        }

        if (!rental.getDateOfRental().isBefore(rental.getReturnDate())) {
            return false;
        }

        List<Rental> existingRentals = findAll();
        for (Rental existingRental : existingRentals) {
            boolean isBefore = existingRental.getReturnDate().isBefore(rental.getDateOfRental()) &&
                    isNotSameDay(existingRental.getReturnDate(), rental.getDateOfRental());
            boolean isAfter = existingRental.getDateOfRental().isAfter(rental.getReturnDate()) &&
                    isNotSameDay(existingRental.getDateOfRental(), rental.getReturnDate());

            if (!(isBefore || isAfter)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if two dates are the same day.
     *
     * @param dateTime1 first date
     * @param dateTime2 second date
     * @return true if both dates are same day, false otherwise
     */
    private boolean isNotSameDay(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.getDayOfYear() != dateTime2.getDayOfYear() ||
                dateTime1.getYear() != dateTime2.getYear();
    }

}
