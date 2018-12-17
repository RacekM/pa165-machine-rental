package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.project.dto.RentalDTO;
import cz.muni.fi.pa165.project.dto.UserDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.format.DateTimeFormatter;


/**
 * Rental rendered to JSON. The @Relation annotation specifies its name in HAL rendering of collections.
 *
 * @author Martin Sisak, 445384
 */

@Relation(value = "rental", collectionRelation = "rentals")
@JsonPropertyOrder({"id", "dateOfRental", "returnDate", "feedback", "machine", "user"})
public class RentalResource extends ResourceSupport {

    @JsonProperty("id") //ResourceSupport alrerady has getId() method
    private long dtoId;
    private String dateOfRental;
    private String returnDate;
    private String feedback;
    private MachineDTO machine;
    private UserDTO user;

    public RentalResource(RentalDTO dto) {
        this.dtoId = dto.getId();
        this.dateOfRental = dto.getDateOfRental().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.returnDate = dto.getReturnDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.feedback = dto.getFeedback();
        this.machine = dto.getMachine();
        this.user = dto.getUser();
    }

    public long getDtoId() {
        return dtoId;
    }

    public void setDtoId(long dtoId) {
        this.dtoId = dtoId;
    }

    public String getDateOfRental() {
        return dateOfRental;
    }

    public void setDateOfRental(String dateOfRental) {
        this.dateOfRental = dateOfRental;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
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
}


