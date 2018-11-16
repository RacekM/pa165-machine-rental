package cz.muni.fi.pa165.project.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * MachineCreateDTO Entity
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */
public class MachineCreateDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MachineCreateDTO)) return false;
        MachineCreateDTO machine = (MachineCreateDTO) o;
        return Objects.equals(getName(), machine.getName());
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MachineCreateDTO{" +
                "name='" + getName() + '\'' +
                '}';
    }

}
