package com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain;

import javax.persistence.AttributeConverter;
import javax.validation.constraints.NotBlank;
import java.util.Locale;

public class PromotionConverter implements AttributeConverter<PromotionSeason, String> {

    @Override
    public String convertToDatabaseColumn(PromotionSeason attribute) {
        return attribute.getClass().getSimpleName().toLowerCase(Locale.ROOT);
    }

    @Override
    public PromotionSeason convertToEntityAttribute(@NotBlank String dbData) {
        return stateOfPromotion(dbData);
    }

    private PromotionSeason stateOfPromotion(String state)
    {
        return state.equals("Easter") ? new PromotionEasterSeason() : new PromotionChristmasSeason();
    }
}
