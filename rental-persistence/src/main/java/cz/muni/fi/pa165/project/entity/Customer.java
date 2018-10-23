package cz.muni.fi.pa165.project.entity;

import cz.muni.fi.pa165.project.enums.CustomerType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(max = 560)
    @NotNull
    @Column(nullable = false)
    private String name;

    @Enumerated
    @NotNull
    private CustomerType customerType;

    public Customer() {
    }

    public Customer(String name, CustomerType customerType) {
        this.name = name;
        this.customerType = customerType;
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
}
