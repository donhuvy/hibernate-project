package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class CustomerDetails {

    @NotNull @Column(nullable = false)
    private String customerName;

    @NotNull @Column(nullable = false)
    private String customerPrename;

    protected CustomerDetails() {}

    public CustomerDetails(String customerName, String customerPrename) {
        this.customerName = customerName;
        this.customerPrename = customerPrename;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPrename() {
        return customerPrename;
    }

    public void setCustomerPrename(String customerPrename) {
        this.customerPrename = customerPrename;
    }
}
