package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "US_PRICE_ID")
public class USPrice  extends Price{

    @Column(name = "SHIPPING_COSTS_IN_US") @NotNull
    private float US_PriceShippmentsCosts = 14.99f;

    @Column(name = "US_CURRENCY") @NotNull
    private String US_Currency;

    protected USPrice() {}

    public USPrice(BigDecimal priceAmount, String US_Currency) {
        super(priceAmount);
        this.US_Currency = US_Currency;
    }

    public float getUS_PriceShippmentsCosts() {
        return US_PriceShippmentsCosts;
    }

    public void setUS_PriceShippmentsCosts(float US_PriceShippmentsCosts) {
        this.US_PriceShippmentsCosts = US_PriceShippmentsCosts;
    }

    public String getUS_Currency() {
        return US_Currency;
    }

    public void setUS_Currency(String US_Currency) {
        this.US_Currency = US_Currency;
    }
}
