package cz.muni.fi.pa165.project.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * RentalCreateDTO entity
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
public class RentalCreateDTO {

    @Past
    @NotNull
    private LocalDateTime dateOfRental;

    @NotNull
    private LocalDateTime returnDate;

    @NotNull
    private String feedback;

    @NotNull
    private MachineDTO machine;

    @NotNull
    private UserDTO user;

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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentalCreateDTO)) return false;
        RentalCreateDTO rental = (RentalCreateDTO) o;
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
        return "RentalCreateDTO{" +
                ", dateOfRental=" + dateOfRental +
                ", returnDate=" + returnDate +
                ", feedback='" + feedback + '\'' +
                ", machine=" + machine +
                ", user=" + user +
                '}';
    }

}
