package com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class ZipCode {

    @Column(nullable = false) @NotNull
    @Size(min = 5, max = 10, message = "ZipCode should have size between {min} and {max}")
    private String zipCode;

    public ZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    protected ZipCode() {}

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
