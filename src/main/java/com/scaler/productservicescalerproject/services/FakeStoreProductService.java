package com.scaler.productservicescalerproject.services;

import com.scaler.productservicescalerproject.dtos.FakeStoreProductDto;
import com.scaler.productservicescalerproject.models.Category;
import com.scaler.productservicescalerproject.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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
        var resultProducts = new ArrayList<Product>();
        var productsDto = restTemplate.getForObject("https://fakestoreapi.com/products/",FakeStoreProductDto[].class);
        if (productsDto != null) {
            for (var productDto : productsDto) {
                var prod = ConvertFakeStoreProductDtoToProduct(productDto);
                resultProducts.add(prod);
            }
        }
        return resultProducts;
    }
    @Override
    public ArrayList<Product> getLimitedProducts(int limit){
        var resultProducts = new ArrayList<Product>();
        FakeStoreProductDto[] productsDto = restTemplate.getForObject("https://fakestoreapi.com/products?limit="+limit,FakeStoreProductDto[].class);
        if (productsDto != null) {
            for (var productDto : productsDto) {
                var prod = ConvertFakeStoreProductDtoToProduct(productDto);
                resultProducts.add(prod);
            }
        }
        return resultProducts;
    }

    @Override
    public List<Product> getProductsInCategory(String categoryName) {
        var resultProducts = new ArrayList<Product>();
        FakeStoreProductDto[] productsDto = restTemplate.getForObject("https://fakestoreapi.com/products/category/"+categoryName,FakeStoreProductDto[].class);
        if (productsDto != null) {
            for (var productDto : productsDto) {
                var prod = ConvertFakeStoreProductDtoToProduct(productDto);
                resultProducts.add(prod);
            }
        }
        return resultProducts;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        try {
            FakeStoreProductDto replaceProd = ConvertProductToFakeStoreProductDto(product);
            var requestCallback = restTemplate.httpEntityCallback(replaceProd, FakeStoreProductDto.class);
            HttpMessageConverterExtractor<FakeStoreProductDto> msgExt = new HttpMessageConverterExtractor<>(FakeStoreProductDto.class,restTemplate.getMessageConverters());
            var productDto = restTemplate.execute("https://fakestoreapi.com/products/"+id,HttpMethod.PATCH, requestCallback, msgExt);
            if (productDto != null) {
                return (ConvertFakeStoreProductDtoToProduct(productDto));
            }
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        return new Product();
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto replaceProd = ConvertProductToFakeStoreProductDto(product);
        HttpEntity<FakeStoreProductDto> requestEntity = new HttpEntity<>(replaceProd);
        ResponseEntity<FakeStoreProductDto> productDto = restTemplate.exchange("https://fakestoreapi.com/products/"+id,HttpMethod.PUT, requestEntity, FakeStoreProductDto.class);
        if (productDto.getBody() != null) {
            return ConvertFakeStoreProductDtoToProduct(productDto.getBody());
        }
        return null;
    }

    @Override
    public Product deleteProduct(Long id) {
        var response = restTemplate.exchange("https://fakestoreapi.com/products/"+id, HttpMethod.DELETE,null, FakeStoreProductDto.class);
        return response.hasBody() && response.getBody() != null ? ConvertFakeStoreProductDtoToProduct(response.getBody()) : new Product();
    }

    @Override
    public Product addProduct(Product product) {
        var fakeStoredto = ConvertProductToFakeStoreProductDto(product);
        var response = restTemplate.postForObject("https://fakestoreapi.com/products",fakeStoredto,FakeStoreProductDto.class);
        if(response != null){
            return ConvertFakeStoreProductDtoToProduct(response);
        }
        return null;
    }

    private Product ConvertFakeStoreProductDtoToProduct(FakeStoreProductDto productDto){
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setId(productDto.getId());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(productDto.getCategory());

        return product;
    }

    private FakeStoreProductDto ConvertProductToFakeStoreProductDto(Product product){
        FakeStoreProductDto fp = new FakeStoreProductDto();
        if(product.getTitle() != null)
            fp.setTitle(product.getTitle());
        if(product.getPrice() < 0)
            fp.setPrice(product.getPrice());
        if(product.getDescription() != null)
            fp.setDescription(product.getDescription());
        if(product.getImageUrl() != null)
            fp.setImage(product.getImageUrl());
        if(product.getCategory() != null && product.getCategory().getName() != null)
            fp.setCategory(product.getCategory().getName());
        return fp;
    }
}
