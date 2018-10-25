package cz.muni.fi.pa165.project.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;


/**
 * Entity Revision represents record of revision belonging to particular machine
 * @author Juraj Vandor
 */
@Entity
public class Revision {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private boolean result;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(nullable = false)
    private Date date;

    @ManyToOne(optional = false)
    @NonNull
    private Machine machine;

    public Revision(){}

    public Revision(boolean result, Date date, Machine machine) {
        this.result = result;
        this.date = date;
        this.machine = machine;
    }

    public Long getId() {
        return id;
    }

    public boolean isResult() {
        return result;
    }

    public Date getDate() {
        return date;
    }

    public Machine getMachine() {
        return machine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Revision)) return false;
        Revision revision = (Revision) o;
        return isResult() == revision.isResult() &&
                Objects.equals(getDate(), revision.getDate()) &&
                Objects.equals(getMachine(), revision.getMachine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isResult(), getDate(), getMachine());
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
