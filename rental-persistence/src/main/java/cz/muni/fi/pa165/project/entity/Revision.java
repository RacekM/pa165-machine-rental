package cz.muni.fi.pa165.project.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * Revision Entity
 *
 * @author Juraj Vandor
 */
@Entity
public class Revision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean result;

    @Past
    @NotNull
    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    @NotNull
    private Machine machine;

    public Revision(){}

    public Revision(boolean result, LocalDateTime date, Machine machine) {
        this.result = result;
        this.date = date;
        this.machine = machine;
    }

    public Long getId() {
        return id;
    }

    public boolean getResult() {
        return result;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Revision)) return false;
        Revision revision = (Revision) o;
        return getResult() == revision.getResult() &&
                Objects.equals(getDate(), revision.getDate()) &&
                Objects.equals(getMachine(), revision.getMachine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResult(), getDate(), getMachine());
    }

    @Override
    public String toString() {
        return "Revision{" +
                "id=" + id +
                ", result=" + result +
                ", date=" + date +
                ", machine=" + machine +
                '}';
    }
}
