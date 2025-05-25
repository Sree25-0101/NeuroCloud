package com.neurocloud.productservice.controller;


import com.neurocloud.productservice.model.Product;
import com.neurocloud.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    //create product
    @PostMapping("/createProduct")
    public Product createProduct(@RequestBody Product product)
    {
        return productRepository.save(product);
    }

    //Get all the products
    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts (){
        return productRepository.findAll();
    }

    //Get Product by product Id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return productRepository.findById(id).orElse(null);
    }

}
