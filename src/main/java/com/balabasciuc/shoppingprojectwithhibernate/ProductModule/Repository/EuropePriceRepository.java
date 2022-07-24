package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Repository;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.EuropePrice;
import org.springframework.stereotype.Repository;

@Repository
public interface EuropePriceRepository extends PriceRepository<EuropePrice, Long> {
}
