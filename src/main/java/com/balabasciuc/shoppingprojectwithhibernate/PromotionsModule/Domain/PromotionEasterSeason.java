package com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "easterPromotion")
public class PromotionEasterSeason implements PromotionSeason{

    private double promotionProcentToDiscount = 10.99f;

    @Override
    public String isSeason() {
        return "Is Easter Season Discount Time of the Year again!";
    }

    @Override
    public double applySeasonPromotionDiscount(double initialPrice) {
        System.out.println("Now you have to pay less with: " + calculateDiscount(initialPrice) + ", instead of: " + initialPrice);
        return calculateDiscount(initialPrice);
    }

    private double calculateDiscount(double initialPriceToDiscount)
    {
        return this.promotionProcentToDiscount  / initialPriceToDiscount;
    }
}
