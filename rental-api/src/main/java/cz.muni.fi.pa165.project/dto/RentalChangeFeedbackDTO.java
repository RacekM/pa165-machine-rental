package cz.muni.fi.pa165.project.dto;

import java.util.Objects;

/**
 * RentalChangeFeedbackDTO entity
 *
 * @author Adam Vanko (445310@mail.muni.cz)
 */
public class RentalChangeFeedbackDTO {

    private RentalDTO rental;

    private String feedback;

    public RentalDTO getRental() {
        return rental;
    }

    public void setRental(RentalDTO rental) {
        this.rental = rental;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentalChangeFeedbackDTO)) return false;
        RentalChangeFeedbackDTO rental = (RentalChangeFeedbackDTO) o;
        return Objects.equals(getRental(), rental.getRental()) &&
                Objects.equals(getFeedback(), rental.getFeedback());
    }

    @Override
    public int hashCode() {
        return Objects.hash(rental, feedback);
    }

    @Override
    public String toString() {
        return "RentalChangeFeedbackDTO{" +
                "rental=" + rental +
                ", feedback='" + feedback + '\'' +
                '}';
    }

}
