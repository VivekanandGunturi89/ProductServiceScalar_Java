package com.scaler.productservicescalerproject.services;

import com.scaler.productservicescalerproject.models.Category;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreCategoryService implements CategoryService{
    private RestTemplate restTemplate;

    public FakeStoreCategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Category> getAllCategories() {
        var resultCategories = new ArrayList<Category>();
        var categories = restTemplate.getForObject("https://fakestoreapi.com/products/categories",String[].class);

        long id = 1L;
        for (var category : categories){
            var newcategory = new Category();
            newcategory.setId(id);
            newcategory.setName(category);
            resultCategories.add(newcategory);
            id += 1;
        }

        return resultCategories;
    }
}
