package com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.Service;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.*;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.PromotionUtility.PromotionCallingOthers;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.Repository.PromotionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.source.internal.hbm.EntityHierarchyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ejb.EJBTransactionRequiredException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Optional;


@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionCallingOthers promotionCallingOthers;

    @PersistenceContext
    private EntityManager entityManager;



    @Autowired
    public PromotionService(PromotionRepository promotionRepository, PromotionCallingOthers promotionCallingOthers) {
        this.promotionRepository = promotionRepository;
        this.promotionCallingOthers = promotionCallingOthers;
    }

    public void createPromotion(Promotion promotion)
    {
        this.promotionRepository.save(promotion);
    }

    public void addProducts(Promotion promotion, String productName) {
        Promotion createdPromotion = new Promotion(promotion.getPromotionSeason());

        ResponseEntity<Product> productResponseEntity = promotionCallingOthers.callProduct(productName);
        Session session = entityManager.unwrap(Session.class);
        session.update(productResponseEntity.getBody()); // for detached entity error

        createdPromotion.addProduct(productResponseEntity.getBody());

        double price = createdPromotion.getProductList().get(0).getProductPrice();
        double discountedPrice = createdPromotion.getPromotionSeason().applySeasonPromotionDiscount(price);


        double priceTo = getDigitsFormat(price - discountedPrice);
        Objects.requireNonNull(productResponseEntity.getBody()).setProductPrice(priceTo);

        createdPromotion.setNumberOfProductsAtPromotion(productResponseEntity.getBody().getProductQuantity());
        this.promotionRepository.save(createdPromotion);

    }

    private double getDigitsFormat(double numberToFormat)
    {
        DecimalFormat formatDecimal = new DecimalFormat("#.##");
        return Double.parseDouble(formatDecimal.format(numberToFormat));
    }

    public Promotion createPromotionWithType(String promotionType) {
        Promotion promotion = new Promotion();
        promotion.setPromotionSeason(setPromotionSeasonImplBasedOnType(promotionType));
        promotionRepository.save(promotion);
        return promotion;
    }

    public Promotion getPromotionSeasonBasedOnSomething(String promotionType)
    {
        PromotionSeason promotionSeason = setPromotionSeasonImplBasedOnType(promotionType);
        Promotion promotion = promotionRepository.findPromotionByPromotionSeason(promotionSeason);
        System.out.println(promotion.getPromotionSeason());
        return promotion;
    }

    private PromotionSeason setPromotionSeasonImplBasedOnType(String promotionType)
    {
        // eh, state pattern would be better i guess
        switch (promotionType.toLowerCase()) {
            case "christmas":
                return new PromotionChristmasSeason();
            case "easter":
                return new PromotionEasterSeason();
            default:
                return new NoPromotionForYouThisTimeMUHAHA();
        }
    }




    public Promotion test(String testam) {
        PromotionSeason promotionSeason = checkPromotionSeason(testam);
        System.out.println(promotionSeason.isSeason());


        Promotion promotion = promotionRepository.findWhatPromotionSeasonWeHave(promotionSeason);


        if (promotion == null) {

            Promotion promotion1 = new Promotion(promotionSeason);
            //?
            promotion = promotion1;
            promotion.setPromotionStore(promotion1.getPromotionStore());
            promotionRepository.save(promotion);
        }

        //urat workaround
        promotion.setPromotionSeason(promotionSeason); // atentie la null
        return promotion;
    }





    private PromotionSeason checkPromotionSeason(String promotionSeason)
    {
        System.out.println("abc este \n" + promotionSeason.toLowerCase());
        switch (promotionSeason.toLowerCase().trim())
        {
            case "easter" :
                return new PromotionEasterSeason();
            case "christmas" :
                return new PromotionChristmasSeason();

            default:
                return new NoPromotionForYouThisTimeMUHAHA();
        }
    }

   /* private PromotionSeason checkPromotionEtc(String promotionSeason)
    {
        System.out.println("urmeaza: ");

        switch (promotionSeason.toLowerCase().trim())
        {
            case "easter" : {
                return new PromotionEasterSeason();
            }
            default: return new NoPromotionForYouThisTimeMUHAHA();
        }
    }
*/
}
