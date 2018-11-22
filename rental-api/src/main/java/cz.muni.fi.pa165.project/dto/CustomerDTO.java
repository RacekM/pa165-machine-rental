package cz.muni.fi.pa165.project.dto;

import cz.muni.fi.pa165.project.enums.CustomerType;

import java.util.Objects;

/**
 * Entity CustomerDTO
 *
 * @author Martin Sisak, 445384
 */
public class CustomerDTO {

    private Long id;

    private String name;

    private CustomerType customerType;


    private Long getId(){ return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerType getCustomerType() { return customerType; }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }


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
                "id=" + id +
                ", name='" + name + '\'' +
                ", customerType=" + customerType +
                '}';
    }
}
