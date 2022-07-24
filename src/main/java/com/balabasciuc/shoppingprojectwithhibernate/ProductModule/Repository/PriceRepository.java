package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Repository;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository<T extends Price, ID> extends JpaRepository<T, ID> {
}
