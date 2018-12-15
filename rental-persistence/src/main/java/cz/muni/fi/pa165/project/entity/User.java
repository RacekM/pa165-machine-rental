package cz.muni.fi.pa165.project.entity;

import cz.muni.fi.pa165.project.enums.UserType;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * User Entity
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */
@Entity( name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    @NotNull
    //todo unique
    private String username;

//    @NotNull
    private String passwordHash;

    @Size(max = 560)
    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String name;

    @Enumerated
    @NotNull
    private UserType userType;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String name, UserType userType) {
        this.name = name;
        this.userType = userType;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;
        return Objects.equals(getName(), user.getName()) &&
                Objects.equals(getPasswordHash(), user.getPasswordHash()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                getUserType() == user.getUserType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getUserType(), getPasswordHash(), getUsername());
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", name='" + name + '\'' +
                ", userType=" + userType +
                '}';
    }
}
