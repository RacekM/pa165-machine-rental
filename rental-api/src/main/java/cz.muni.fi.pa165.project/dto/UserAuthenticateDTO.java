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

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
                Objects.equals(getUserName(), customer.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassword());
    }

    @Override
    public String toString() {
        return "UserAuthenticateDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
