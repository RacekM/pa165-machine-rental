package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.muni.fi.pa165.project.dto.MachineDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

/**
 * Category rendered to JSON. The @Relation annotation specifies its name in HAL rendering of collections.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
@Relation(value = "machine", collectionRelation = "machines")
@JsonPropertyOrder({"id", "name"})
public class MachineResource extends ResourceSupport {

    @JsonProperty("id") //ResourceSupport alrerady has getId() method
    private long dtoId;
    private String name;

    public MachineResource(MachineDTO dto) {
        this.dtoId = dto.getId();
        this.name = dto.getName();
    }

    public long getDtoId() {
        return dtoId;
    }

    public void setDtoId(long dtoId) {
        this.dtoId = dtoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
