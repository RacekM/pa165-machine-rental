package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.project.dto.RentalDTO;
import cz.muni.fi.pa165.project.dto.UserDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Relation(value = "rental", collectionRelation = "rentals")
@JsonPropertyOrder({"id", "dateOfRental", "returnDate", "note", "machine", "user", "lastRevision", "revisionDate"})
public class RentalRevisionResource extends ResourceSupport {

    @JsonProperty("id") //ResourceSupport alrerady has getId() method
    private long dtoId;
    private String dateOfRental;
    private String returnDate;
    private String note;
    private MachineDTO machine;
    private UserDTO user;
    private String lastRevision;
    private String revisionDate;

    public RentalRevisionResource(RentalDTO dto, String lastRevision, LocalDateTime revisionDate) {
        this.dtoId = dto.getId();
        this.dateOfRental = dto.getDateOfRental().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.returnDate = dto.getReturnDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.note = dto.getNote();
        this.machine = dto.getMachine();
        this.user = dto.getUser();
        this.lastRevision = lastRevision;
        this.revisionDate = revisionDate == null ? "no revision" : revisionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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

    public String getLastRevision() {
        return lastRevision;
    }

    public void setLastRevision(String lastRevision) {
        this.lastRevision = lastRevision;
    }

    public String getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(String revisionDate) {
        this.revisionDate = revisionDate;
    }
}
