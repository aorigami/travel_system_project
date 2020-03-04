package com.travel.service;

import com.travel.pojo.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll(Integer page, Integer rows);

    void addProduct(Product product);

    void delProducts(List<String> productIds);

    void setProductStatus(List<String> productIds, Integer status);
}
