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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    @NotNull
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    public Rental() {

    }

    public Rental(LocalDateTime dateOfRental, LocalDateTime returnDate, String feedback, Machine machine, User user) {
        this.dateOfRental = dateOfRental;
        this.returnDate = returnDate;
        this.feedback = feedback;
        this.machine = machine;
        this.user = user;
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

    public User getUser() { return user; }

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

    public void setUser(User user) {
        this.user = user;
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
                Objects.equals(getUser(), rental.getUser());
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateOfRental, returnDate, feedback, machine, user);
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", dateOfRental=" + dateOfRental +
                ", returnDate=" + returnDate +
                ", feedback='" + feedback + '\'' +
                ", machine=" + machine +
                ", user=" + user +
                '}';
    }
}
