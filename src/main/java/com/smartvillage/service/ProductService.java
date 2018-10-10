package com.smartvillage.service;

import com.smartvillage.entity.Product;

public interface ProductService {

    void add(Product product);
    Product findById(Long Id);
}
