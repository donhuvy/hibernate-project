package com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Service;

import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Domain.Category;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Store;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.StoreType;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Repository.StoreRepository;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Utility.CallingOthers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotNull;


@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired private CallingOthers callingOthers;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public void save(Store store)
    {
        storeRepository.save(store);
    }



    public Store getStore(@NotNull String storeType) {
        return storeRepository.getStoreByStoreType(StoreType.valueOf(storeType));

    }

    public Store addCategory(String storeType, String category) {
        Store store = getStore(storeType);
        ResponseEntity<Category> category1 = callingOthers.getCategory(category);
        System.out.println(category1.getBody().getPromotion().getPromotionSeason().isSeason());


        store.getCategorySet().forEach(element -> System.out.println(element.getPromotion().getPromotionSeason()));
        store.add(category1.getBody());
        store.getCategorySet().forEach(element -> System.out.println(element.getPromotion().getPromotionSeason()));



        storeRepository.save(store);

        return getStore(storeType);

    }

    public Store addCustomer(String customerName, String storeName) {
        Store store = getStoreByStoreName(storeName);

        store.getCustomerCollection().add(callingOthers.findCustomerWithName(customerName).getBody());
        storeRepository.save(store);
        return getStoreByStoreName(storeName);
    }

    public Store getStoreByStoreName(String storeName)
    {
        return storeRepository.getStoreByStoreName(storeName);
    }
}
