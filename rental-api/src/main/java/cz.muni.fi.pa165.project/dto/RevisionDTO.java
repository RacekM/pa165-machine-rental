package cz.muni.fi.pa165.project.dto;

import java.util.Calendar;
import java.util.Objects;

/**
 * @author Juraj Vandor
 */
public class RevisionDTO {
    private Long id;
    private boolean result;
    private Calendar date;
    private MachineDTO machine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public MachineDTO getMachine() {
        return machine;
    }

    public void setMachine(MachineDTO machine) {
        this.machine = machine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RevisionDTO)) return false;
        RevisionDTO that = (RevisionDTO) o;
        return getResult() == that.getResult() &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getMachine(), that.getMachine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResult(), getDate(), getMachine());
    }

    @Override
    public String toString() {
        return "RevisionDTO{" +
                "id=" + id +
                ", result=" + result +
                ", date=" + date +
                ", machine=" + machine +
                '}';
    }
}
