package cz.muni.fi.pa165.project.entity;

import cz.muni.fi.pa165.project.enums.CustomerType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Customer Entity
 *
 * @author Matus Racek (mat.racek@gmail.com)
 */
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(max = 560)
    @NotNull
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "customer")
    @NotNull
    private List<Rental> rentals = new ArrayList<>();

    @Enumerated
    @NotNull
    private CustomerType customerType;

    public Customer() {
    }

    public Customer(String name, CustomerType customerType) {
        this.name = name;
        this.customerType = customerType;
    }

    public List<Rental> getRentals() {
        return Collections.unmodifiableList(rentals);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;
        return Objects.equals(getName(), customer.getName()) &&
                getCustomerType() == customer.getCustomerType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCustomerType());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customerType=" + customerType +
                '}';
    }
}
