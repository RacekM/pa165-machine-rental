package cz.muni.fi.pa165.project.entity;

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

    public Rental(){

    }

    public Rental(Date dateOfRental, Date returnDate, String feedback){
        this.dateOfRental = dateOfRental;
        this.returnDate = returnDate;
        this.feedback = feedback;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return Objects.equals(dateOfRental, rental.dateOfRental) &&
                Objects.equals(returnDate, rental.returnDate) &&
                Objects.equals(feedback, rental.feedback);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateOfRental, returnDate, feedback);
    }
}
