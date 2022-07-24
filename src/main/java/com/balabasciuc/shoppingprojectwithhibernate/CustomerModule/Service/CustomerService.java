package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Service;

import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.Customer;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Repository.CustomerRepository;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.UtilityProjections.CustomerCalling;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.UtilityProjections.CustomerProjection;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.UtilityProjections.ProductOutOfStockException;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerCalling customerCalling;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerCalling customerCalling) {
        this.customerRepository = customerRepository;
        this.customerCalling = customerCalling;
    }

    public void createCustomer(CustomerProjection.CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getCustomerAddress());
        customerRepository.save(customer);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void buyProducts(@NotBlank String productName, int numberOfProductsToBuy)
    {
        Product product = checkProduct(productName);
        product.setProductQuantity(product.getProductQuantity() - numberOfProductsToBuy);
    }

    private Product checkProduct(String productToCheck)
    {
        Product product = customerCalling.callProductByName(productToCheck);
        if(product.getProductQuantity() < 1)
        {
            throw new ProductOutOfStockException("Sorry, we need to refill this product");
        }
        return product;
    }

    public CustomerProjection.CustomerDTO getCustomerByName(String name) {

        return customerRepository.getCustomerByCustomerAddress_CustomerDetails_CustomerName(name);
    }

    public CustomerProjection.CustomerFat getCustomerById(Long customerId)
    {
        return customerRepository.getFatCustomerByCustomerId(customerId);
    }

    public Customer customerInStore(String customerName, String storeName) {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.getCustomerByName(customerName));
        customer.ifPresent(value -> value.addStore(customerChooseStore(storeName)));
        Store store = customerChooseStore(storeName);
        store.addCustomer(customer.get());
        return customer.get();
    }

    private Store customerChooseStore(String storeType)
    {
        return customerCalling.callStore(storeType);
    }


    public Customer getWholeCustomerByName(String name) {
        return customerRepository.getCustomerByName(name);
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}
