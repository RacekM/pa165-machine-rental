package cz.muni.fi.pa165.project.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(nullable = false)
    private Date dateOfRental;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(nullable = false)
    private Date returnDate;

    @NotNull
    @Column(nullable = false)
    private String feedback;

    @ManyToOne(optional = false)
    @NonNull
    private Machine machine;

    @ManyToOne(optional = false)
    @NonNull
    private Customer customer;

    public Rental(){

    }

    public Rental(Date dateOfRental, Date returnDate, String feedback, Machine machine, Customer customer){
        this.dateOfRental = dateOfRental;
        this.returnDate = returnDate;
        this.feedback = feedback;
        this.machine = machine;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public Date getDateOfRental() {
        return dateOfRental;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public Machine getMachine() { return machine; }

    public Customer getCustomer() { return customer; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rental)) return false;
        Rental rental = (Rental) o;
        return Objects.equals(getDateOfRental(), rental.dateOfRental) &&
                Objects.equals(getReturnDate(), rental.returnDate) &&
                Objects.equals(getFeedback(), rental.feedback) &&
                Objects.equals(getMachine(), rental.machine) &&
                Objects.equals(getCustomer(), rental.customer);
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