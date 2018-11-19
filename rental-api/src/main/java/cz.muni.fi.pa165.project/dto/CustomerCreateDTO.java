package cz.muni.fi.pa165.project.dto;

import cz.muni.fi.pa165.project.enums.CustomerType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Entity CustomerCreateDTO
 *
 * @author Martin Sisak, 445384
 */

public class CustomerCreateDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    private CustomerType customerType;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public CustomerType getCustomerType() { return customerType; }

    public void setCustomerType(CustomerType customerType) { this.customerType = customerType; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDTO)) return false;
        CustomerDTO customer = (CustomerDTO) o;
        return Objects.equals(getName(), customer.getName()) &&
                getCustomerType() == customer.getCustomerType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCustomerType());
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                ", name='" + name + '\'' +
                ", customerType=" + customerType +
                '}';
    }
}
