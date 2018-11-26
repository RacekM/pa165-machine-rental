package cz.muni.fi.pa165.project.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *  Rental Entity
 * @author Martin Sisak (445384@mail.muni.cz)
 */
@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dateOfRental;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime returnDate;

    @NotNull
    @Column(nullable = false)
    private String feedback;

    @ManyToOne(optional = false)
    @NotNull
    private Machine machine;

    @ManyToOne(optional = false)
    @NotNull
    private Customer customer;

    public Rental() {

    }

    public Rental(LocalDateTime dateOfRental, LocalDateTime returnDate, String feedback, Machine machine, Customer customer) {
        this.dateOfRental = dateOfRental;
        this.returnDate = returnDate;
        this.feedback = feedback;
        this.machine = machine;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateOfRental() {
        return dateOfRental;
    }

    public void setDateOfRental(LocalDateTime dateOfRental) {
        this.dateOfRental = dateOfRental;
    }

    public String getFeedback() {
        return feedback;
    }

    public Machine getMachine() { return machine; }

    public Customer getCustomer() { return customer; }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rental)) return false;
        Rental rental = (Rental) o;
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
        return "Rental{" +
                "id=" + id +
                ", dateOfRental=" + dateOfRental +
                ", returnDate=" + returnDate +
                ", feedback='" + feedback + '\'' +
                ", machine=" + machine +
                ", customer=" + customer +
                '}';
    }
}
