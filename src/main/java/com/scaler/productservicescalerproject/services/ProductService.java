package com.scaler.productservicescalerproject.services;

import com.scaler.productservicescalerproject.models.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id);
    List<Product> getAllProducts();
}
