package cz.muni.fi.pa165.project.dao;

import cz.muni.fi.pa165.project.entity.Rental;

import java.util.List;

/**
 * @author Adam Vaňko (445310@mail.muni.cz)
 */
public interface RentalDao {

    void create(Rental rental);

    void update(Rental rental);

    Rental findById(Long id);

    List<Rental> findAll();

    void delete(Rental rental);

}
