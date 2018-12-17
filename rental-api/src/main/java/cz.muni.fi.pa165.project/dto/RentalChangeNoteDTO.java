package cz.muni.fi.pa165.project.dto;

import java.util.Objects;

/**
 * RentalChangeNoteDTO entity
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
public class RentalChangeNoteDTO {

    private RentalDTO rental;

    private String note;

    public RentalDTO getRental() {
        return rental;
    }

    public void setRental(RentalDTO rental) {
        this.rental = rental;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentalChangeNoteDTO)) return false;
        RentalChangeNoteDTO rental = (RentalChangeNoteDTO) o;
        return Objects.equals(getRental(), rental.getRental()) &&
                Objects.equals(getNote(), rental.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(rental, note);
    }

    @Override
    public String toString() {
        return "RentalChangeNoteDTO{" +
                "rental=" + rental +
                ", note='" + note + '\'' +
                '}';
    }

}
