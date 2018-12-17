package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.muni.fi.pa165.project.dto.MachineDTO;
import cz.muni.fi.pa165.project.dto.RevisionDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.format.DateTimeFormatter;


/**
 * @author Juraj Vandor
 */
@Relation(value = "revision", collectionRelation = "revisions")
@JsonPropertyOrder({"id", "result", "machine", "date"})
public class RevisionResource extends ResourceSupport {

    @JsonProperty("id") //ResourceSupport alrerady has getId() method
    private long dtoId;
    private String result;
    private MachineDTO machine;
    private String date;

    public RevisionResource(RevisionDTO dto) {
        this.dtoId = dto.getId();
        this.result = dto.getResult() ? "passed" : "failed";
        this.machine = dto.getMachine();
        this.date = dto.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public long getDtoId() {
        return dtoId;
    }

    public void setDtoId(long dtoId) {
        this.dtoId = dtoId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public MachineDTO getMachine() {
        return machine;
    }

    public void setMachine(MachineDTO machine) {
        this.machine = machine;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
