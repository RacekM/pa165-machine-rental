package cz.muni.fi.pa165.project.dto;

import cz.muni.fi.pa165.project.enums.UserType;

import java.util.Objects;

/**
 * Entity CustomerDTO
 *
 * @author Martin Sisak, 445384
 */
public class UserDTO {

    private Long id;

    private String name;

    private UserType userType;

    private String userName;

    private String passwordHash;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getUserType() { return userType; }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO customer = (UserDTO) o;
        return Objects.equals(getName(), customer.getName()) &&
                Objects.equals(getPasswordHash(), customer.getPasswordHash()) &&
                Objects.equals(getUserName(), customer.getUserName()) &&
                getUserType() == customer.getUserType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getUserType());
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + getName() + '\'' +
                ", userType=" + getUserType() +
                ", userName='" + getUserName() + '\'' +
                ", passwordHash='" + getPasswordHash() + '\'' +
                '}';
    }
}
