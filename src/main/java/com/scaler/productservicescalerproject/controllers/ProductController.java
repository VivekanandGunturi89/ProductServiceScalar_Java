package com.scaler.productservicescalerproject.controllers;
import com.scaler.productservicescalerproject.models.Product;
import com.scaler.productservicescalerproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(){
        var products = productService.getAllProducts();
        if(products != null)
            return new ResponseEntity<>(products, HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        var product = productService.getSingleProduct(id);
        if(product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @PostMapping()
    public ResponseEntity<Product> AddProduct(@RequestBody Product product){
        var updatedProduct = productService.addProduct(product);
        if(updatedProduct != null)
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @GetMapping("?limit={limit}")
    public ResponseEntity<List<Product>> GetLimitedProducts(@RequestParam int limit){
        var limitedProducts = productService.getLimitedProducts(limit);
        if(limitedProducts != null)
            return new ResponseEntity<>(limitedProducts,HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> UpdateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        var productUpdate = productService.updateProduct(id, product);
        if(productUpdate.getBody() != null) {
            return new ResponseEntity<>(productUpdate.getBody(),HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> ReplaceProduct(@PathVariable("id") Long id, @RequestBody Product product){
        var replacedProduct = productService.replaceProduct(id, product);
        if(replacedProduct != null)
            return new ResponseEntity<>(replacedProduct,HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> DeleteProduct(@PathVariable("id") Long id){
        var deleted = productService.deleteProduct(id);
        if (deleted != null)
            return  new ResponseEntity<>(true,HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Product>> getProductsInCategory(@PathVariable("categoryName") String categoryName){
        var categoryProducts = productService.getProductsInCategory(categoryName);
        if(categoryProducts != null)
            return  new ResponseEntity<>(categoryProducts,HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
