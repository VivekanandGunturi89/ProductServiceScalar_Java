package com.scaler.productservicescalerproject.controllers;
import com.scaler.productservicescalerproject.models.Product;
import com.scaler.productservicescalerproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping()
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id){
        return productService.getSingleProduct(id);
    }
    @PostMapping()
    public Product AddProduct(@RequestBody Product product){
        Product p = new Product();
        p.setTitle("new Product");
        return p;
    }

    @PatchMapping("/{id}")
    public Product UpdateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return new Product();
    }

    @PutMapping("/{id}")
    public Product ReplaceProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return new Product();
    }

    @DeleteMapping("/{id}")
    public void DeleteProduct(@PathVariable("id") Long id){
        return ;
    }
}
