package cz.muni.fi.pa165.project.dto;

import java.util.Objects;


/**
 * MachineDTO Entity
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */
public class MachineDTO {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MachineDTO)) return false;
        MachineDTO machine = (MachineDTO) o;
        return Objects.equals(getName(), machine.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "MachineDTO{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                '}';
    }


}
