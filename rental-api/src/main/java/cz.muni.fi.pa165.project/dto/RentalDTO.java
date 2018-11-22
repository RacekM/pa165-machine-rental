package cz.muni.fi.pa165.project.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * RentalDTO entity
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
public class RentalDTO {

    private Long id;

    private LocalDateTime dateOfRental;

    private LocalDateTime returnDate;

    private String feedback;

    private MachineDTO machine;

    private CustomerDTO customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateOfRental() {
        return dateOfRental;
    }

    public void setDateOfRental(LocalDateTime dateOfRental) {
        this.dateOfRental = dateOfRental;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public MachineDTO getMachine() {
        return machine;
    }

    public void setMachine(MachineDTO machine) {
        this.machine = machine;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentalDTO)) return false;
        RentalDTO rental = (RentalDTO) o;
        return Objects.equals(getDateOfRental(), rental.getDateOfRental()) &&
                Objects.equals(getReturnDate(), rental.getReturnDate()) &&
                Objects.equals(getFeedback(), rental.getFeedback()) &&
                Objects.equals(getMachine(), rental.getMachine()) &&
                Objects.equals(getCustomer(), rental.getCustomer());
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateOfRental, returnDate, feedback, machine, customer);
    }

    @Override
    public String toString() {
        return "RentalDTO{" +
                "id=" + id +
                ", dateOfRental=" + dateOfRental +
                ", returnDate=" + returnDate +
                ", feedback='" + feedback + '\'' +
                ", machine=" + machine +
                ", customer=" + customer +
                '}';
    }

}