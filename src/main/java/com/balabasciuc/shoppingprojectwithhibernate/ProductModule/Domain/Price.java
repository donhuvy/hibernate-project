package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain;

import javax.enterprise.inject.Default;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.ws.rs.DefaultValue;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Price {

    @Column(name = "PRICE_ID")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long priceId;


    @Positive @NotBlank @Column(nullable = false, name = "PRICE_AMOUNT") @DefaultValue("1.00")
    private BigDecimal priceAmount;

    //this create a fk in our Price table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product productPrice;

    protected Price() {}

    public Price(BigDecimal priceAmount) {
        this.priceAmount = priceAmount;
    }

    public BigDecimal getPrice(Integer price)
    {
        return new BigDecimal(price);
    }

    public BigDecimal getPrice(String price)
    {
        return new BigDecimal(price);
    }

    public BigDecimal getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(BigDecimal priceAmount) {
        this.priceAmount = priceAmount;
    }

    public Long getPriceId() {
        return priceId;
    }

    public Product getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Product productPrice) {
        this.productPrice = productPrice;
    }
}
