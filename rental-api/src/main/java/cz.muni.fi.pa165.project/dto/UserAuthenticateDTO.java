package cz.muni.fi.pa165.project.dto;

import cz.muni.fi.pa165.project.enums.UserType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Entity CustomerCreateDTO
 *
 * @author Martin Sisak, 445384
 */

public class UserAuthenticateDTO {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAuthenticateDTO)) return false;
        UserAuthenticateDTO customer = (UserAuthenticateDTO) o;
        return Objects.equals(getPassword(), customer.getPassword()) &&
                Objects.equals(getUsername(), customer.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassword());
    }

    @Override
    public String toString() {
        return "UserAuthenticateDTO{" +
                "userName='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
