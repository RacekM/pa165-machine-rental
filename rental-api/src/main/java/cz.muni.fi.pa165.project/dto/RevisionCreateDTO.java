package cz.muni.fi.pa165.project.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Juraj Vandor
 */
public class RevisionCreateDTO {
    private boolean result;
    @NotNull
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date = LocalDateTime.now();
    @NotNull
    private Long machine;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getMachine() {
        return machine;
    }

    public void setMachine(Long machine) {
        this.machine = machine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RevisionDTO)) return false;
        RevisionDTO that = (RevisionDTO) o;
        return getResult() == that.getResult() &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getMachine(), that.getMachine().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResult(), getDate(), getMachine());
    }

    @Override
    public String toString() {
        return "RevisionDTO{" +
                "result=" + result +
                ", date=" + date +
                ", machine=" + machine +
                '}';
    }
}
