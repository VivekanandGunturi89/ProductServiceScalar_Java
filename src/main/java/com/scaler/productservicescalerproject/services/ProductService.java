package com.scaler.productservicescalerproject.services;

import com.scaler.productservicescalerproject.exceptions.ProductNotExistsException;
import com.scaler.productservicescalerproject.models.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotExistsException;
    List<Product> getAllProducts();
    List<Product> getLimitedProducts(int limit);
    List<Product> getProductsInCategory(String categoryName);
    Product updateProduct(Long id, Product product);
    Product replaceProduct(Long id, Product product);
    Product deleteProduct(Long id);
    Product addProduct(Product product);
}
