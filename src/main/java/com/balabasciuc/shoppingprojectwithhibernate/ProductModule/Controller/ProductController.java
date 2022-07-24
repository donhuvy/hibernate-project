package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Controller;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/createProduct")
    public ResponseEntity<String> create(@RequestBody Product product) {
        productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product " + product + " was created!");
    }

    @GetMapping(value = "/getProduct/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id)
    {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.findById(id));
    }

    @GetMapping(value = "/getProductByName/{productName}")
    public ResponseEntity<Product> getProductByName(@PathVariable String productName)
    {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getByName(productName));
    }

}
