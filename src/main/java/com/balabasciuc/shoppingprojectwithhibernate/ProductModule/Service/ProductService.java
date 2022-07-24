package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Service;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public Product findById(Long productId)
    {
        return productRepository.findById(productId).get();
    }

    public Product getByName(String productName) {
        return productRepository.findProductByProductDescription_DescriptionName(productName);
    }
}
