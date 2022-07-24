package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "EUROPE_PRICE_ID")
public class EuropePrice extends Price{

    @Column(name = "SHIPPING_COSTS_IN_EU") @NotNull
    private float europePriceShippmentsCosts = 5.99f;

    @Column(name = "CURRENCY_EUROPE") @NotNull
    private String europeCurrency;

    public EuropePrice(BigDecimal priceAmount, String europeCurrency) {
        super(priceAmount);
        this.europeCurrency = europeCurrency;
    }

    protected EuropePrice() {}


    public float getEuropePriceShippmentsCosts() {
        return europePriceShippmentsCosts;
    }

    public void setEuropePriceShippmentsCosts(float europePriceShippmentsCosts) {
        this.europePriceShippmentsCosts = europePriceShippmentsCosts;
    }

    public String getEuropeCurrency() {
        return europeCurrency;
    }

    public void setEuropeCurrency(String europeCurrency) {
        this.europeCurrency = europeCurrency;
    }
}
