package com.scaler.productservicescalerproject.services;

import com.scaler.productservicescalerproject.dtos.FakeStoreProductDto;
import com.scaler.productservicescalerproject.models.Category;
import com.scaler.productservicescalerproject.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;
    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @Override
    public Product getSingleProduct(Long id) {
        FakeStoreProductDto productDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class);
        if (productDto != null) {
            return ConvertFakeStoreProductDtoToProduct(productDto);
        }
        return null;
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> resultProducts = new ArrayList<>();
        FakeStoreProductDto[] productsDto = restTemplate.getForObject("https://fakestoreapi.com/products/",FakeStoreProductDto[].class);
        for (var productDto : productsDto) {
            var prod = ConvertFakeStoreProductDtoToProduct(productDto);
            resultProducts.add(prod);
        }
        return resultProducts;
    }

    private Product ConvertFakeStoreProductDtoToProduct(FakeStoreProductDto productDto){
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setId(productDto.getId());
        product.setPrice(productDto.getPrice());
        product.setDescription(product.getDescription());
        product.setImageUrl(productDto.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(productDto.getCategory());

        return product;
    }
}
