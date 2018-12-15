package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.muni.fi.pa165.project.dto.UserDTO;
import cz.muni.fi.pa165.project.enums.UserType;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

/**
 * User rendered to JSON. The @Relation annotation specifies its name in HAL rendering of collections.
 *
 * @author Matus Racek (445411@mail.muni.cz)
 */
@Relation(value = "user", collectionRelation = "users")
@JsonPropertyOrder({"id", "name", "userName", "password", "userType"})
public class UserResource extends ResourceSupport {

    @JsonProperty("id") //ResourceSupport alrerady has getId() method
    private long dtoId;
    private String name;
    private String userName;
    private String password;
    private UserType userType;

    public UserResource(UserDTO dto) {
        this.dtoId = dto.getId();
        this.name = dto.getName();
        this.userName = dto.getUserName();
        this.password = dto.getPasswordHash();
        this.userType = dto.getUserType();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
