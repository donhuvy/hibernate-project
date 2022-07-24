package com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Repository;

import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Store;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.StoreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Store getStoreByStoreType(StoreType storeType);

    Store getStoreByStoreName(String storeName);
}

