package com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain;

import com.sun.istack.NotNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Location {

    @Column(nullable = false) @NotNull
    private String locationCity;
    @Column(nullable = false) @NotNull
    private String locationCountry;

    @Embedded
    @NotNull
    @Column(nullable = false)
    @AttributeOverride(name = "zipCode", column = @Column(name = "LOCATION_ZIP_CODE"))
    private ZipCode locationZipCode;

    protected Location() {}

    public Location(String locationCity, String locationCountry, ZipCode locationZipCode) {
        this.locationCity = locationCity;
        this.locationCountry = locationCountry;
        this.locationZipCode = locationZipCode;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationCountry() {
        return locationCountry;
    }

    public void setLocationCountry(String locationCountry) {
        this.locationCountry = locationCountry;
    }

    public ZipCode getLocationZipCode() {
        return locationZipCode;
    }

    public void setLocationZipCode(ZipCode locationZipCode) {
        this.locationZipCode = locationZipCode;
    }
}
