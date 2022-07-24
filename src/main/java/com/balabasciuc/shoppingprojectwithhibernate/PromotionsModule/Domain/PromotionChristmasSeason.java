package com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.ws.rs.DefaultValue;

@JsonTypeName(value = "christmasPromotion")
public class PromotionChristmasSeason implements PromotionSeason {

    @DefaultValue("10.2")
    private float promotionDiscountForChristmasIs;

    @Override
    public String isSeason() {
        return "HO HO HO, SANTA GIVES YOU SOME BIG DISCOUNT THIS YEAR!";
    }

    @Override
    public double applySeasonPromotionDiscount(double initialPrice) {

        isSeason();
        System.out.println("So you have to pay ONLY ONLY ONLY " + calculateDiscount(initialPrice));
        return calculateDiscount(initialPrice);
    }

    private double calculateDiscount(double amountToPay)
    {
        //hard coded
        this.promotionDiscountForChristmasIs = 2.99f;
        double reducedAmount = (promotionDiscountForChristmasIs * 100) / amountToPay;
        return amountToPay - reducedAmount;
    }

    public float getPromotionDiscountForChristmasIs() {
        return promotionDiscountForChristmasIs;
    }

    public void setPromotionDiscountForChristmasIs(float promotionDiscountForChristmasIs) {
        this.promotionDiscountForChristmasIs = promotionDiscountForChristmasIs;
    }
}
