package com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Controller;

import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Store;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping(value = "/create")
    public void createStore(@RequestBody Store store)
    {
        storeService.save(store);
    }

    @GetMapping(value = "/{storeType}")
    public Store getStore(@PathVariable("storeType") String storeType)
    {
        return storeService.getStore(storeType);
    }

    @GetMapping(value = "getStoreByName/{storeName}")
    public Store getStoreByName(@PathVariable String storeName)
    {
        return storeService.getStoreByStoreName(storeName);
    }
    @GetMapping(value = "/addCategory/{storeType}/{category}")
    public Store addCategoryToStore(@PathVariable("storeType") String storeType, @PathVariable ("category") String category)
    {
         return storeService.addCategory(storeType, category);

    }

    @GetMapping(value = "/addCustomer/{customerName}/{storeName}")
    public Store addCustomerToStore(@PathVariable String customerName, @PathVariable String storeName)
    {
        return storeService.addCustomer(customerName, storeName);
    }


}
