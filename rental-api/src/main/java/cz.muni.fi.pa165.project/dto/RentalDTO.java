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

    private String note;

    private MachineDTO machine;

    private UserDTO user;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
        if (!(o instanceof RentalDTO)) return false;
        RentalDTO rental = (RentalDTO) o;
        return Objects.equals(getDateOfRental(), rental.getDateOfRental()) &&
                Objects.equals(getReturnDate(), rental.getReturnDate()) &&
                Objects.equals(getNote(), rental.getNote()) &&
                Objects.equals(getMachine(), rental.getMachine()) &&
                Objects.equals(getUser(), rental.getUser());
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateOfRental, returnDate, note, machine, user);
    }

    @Override
    public String toString() {
        return "RentalDTO{" +
                "id=" + id +
                ", dateOfRental=" + dateOfRental +
                ", returnDate=" + returnDate +
                ", note='" + note + '\'' +
                ", machine=" + machine +
                ", user=" + user +
                '}';
    }

}
