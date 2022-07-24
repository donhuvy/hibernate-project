package com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Service;

import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Domain.Category;
import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Repository.CategoryRepository;
import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Utility.Projections.CallingFromCategory;
import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Utility.Projections.CategoryProjections;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Repository.ProductRepository;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.Promotion;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CallingFromCategory callingFromCategoryTo;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CallingFromCategory callingFromCategoryTo) {
        this.categoryRepository = categoryRepository;
        this.callingFromCategoryTo = callingFromCategoryTo;
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public Category findCategoryById(Long categoryId) {

        return categoryRepository.findById(categoryId).get();
    }

    public Category getCategoryByName(String categoryName) {
        Category category =  categoryRepository.getCategoryByCategoryDescription_DescriptionName(categoryName);
        return category;
    }

    public List<CategoryProjections.DescriptionOnly> findByCategoryDescription(Long categoryId) {
        return categoryRepository.findByCategoryId(categoryId);
    }


    public Category addProductForThisCategory(String categoryName, String productName) {

        Category category = getCategoryByName(categoryName);
        ResponseEntity<Product> product = callingFromCategoryTo.callProduct(productName);

        category.addProduct(product.getBody());
        categoryRepository.save(category);
        return category;
    }

    public Category addPromotionToCategory(String categoryName, String promotionType) {

        Category category = discountPromotionForCategory(categoryName, promotionType);



        categoryRepository.save(category);
        return category;
    }
    private Category discountPromotionForCategory(String categoryName, String promotionType)
    {
        Category category = getCategoryByName(categoryName);
        Promotion promotion = callPromotion(promotionType);


        category.getProductCollection().stream().forEach(product -> promotion.addProduct(product));

        promotion.setNumberOfProductsAtPromotion(numberOfProductsAtPromotion(category.getProductCollection()));

     //   promotion.getProductList().forEach(product -> promotion.setNumberOfProductsAtPromotion(product.getProductQuantity()));



        //vezi ca daca aplici 2 sezoane de reduceri consecutive, se aduna
        //adica nu e: reducere -> revine la cost initial -> reducere
        //ci din cost initial -> reducere -> reducere
        //poti face asta de mai jos intr-o metoda
        category.getProductCollection()
                .forEach(product -> product.setProductPrice(
                        discountedPrice(product.getProductPrice(), promotion.getPromotionSeason().applySeasonPromotionDiscount(product.getProductPrice()))));

        Session session = entityManager.unwrap(Session.class);
        session.update(promotion);
        category.setPromotion(promotion);
        System.out.println(category.getPromotion().getPromotionSeason().isSeason());
        categoryRepository.save(category);
        System.out.println(category.getPromotion().getPromotionSeason().isSeason());
        return category;
    }

    private double discountedPrice(double initialPrice, double discountedProcent)
    {
            return getDigitsFormat(initialPrice - discountedProcent);
    }

    private double getDigitsFormat(double numberToFormat)
    {
        DecimalFormat formatDecimal = new DecimalFormat("#.##");
        return Double.parseDouble(formatDecimal.format(numberToFormat));
    }

    private int numberOfProductsAtPromotion(Collection<Product> productCollection)
    {
         return productCollection.stream().mapToInt(Product::getProductQuantity).sum();

    }


    private Promotion callPromotion(String promotionType)
    {
       return callingFromCategoryTo.callPromotion(promotionType).getBody();
    }


}
