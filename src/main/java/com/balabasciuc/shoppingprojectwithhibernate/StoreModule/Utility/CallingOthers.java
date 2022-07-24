package com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Utility;

import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Domain.Category;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CallingOthers {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<Category> getCategory(String categoryName)
    {
        return restTemplate.getForEntity("http://locaLhost:8080/categories/name/"+categoryName, Category.class);
    }

    public ResponseEntity<Customer> findCustomerWithName(String customerName) {
        return restTemplate.getForEntity("http://localhost:8080/customers/getWholeCustomerByName?name="+customerName, Customer.class);
    }
}
