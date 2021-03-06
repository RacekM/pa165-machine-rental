package cz.muni.fi.pa165.project.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * RentalCreateDTO entity
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
public class RentalCreateDTO {

    @NotNull
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateOfRental = LocalDateTime.now();

    @NotNull
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime returnDate = LocalDateTime.now();

    @NotNull
    private String note;

    @NotNull
    private Long machine;

    @NotNull
    private Long user;

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

    public Long getMachine() {
        return machine;
    }

    public void setMachine(Long machine) {
        this.machine = machine;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentalCreateDTO)) return false;
        RentalCreateDTO rental = (RentalCreateDTO) o;
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
        return "RentalCreateDTO{" +
                ", dateOfRental=" + dateOfRental +
                ", returnDate=" + returnDate +
                ", note='" + note + '\'' +
                ", machine=" + machine +
                ", user=" + user +
                '}';
    }

}
