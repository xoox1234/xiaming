package com.smartvillage.service.impl;


import java.util.Optional;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.smartvillage.dao.ProductRepository;
import com.smartvillage.entity.Product;
import com.smartvillage.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Resource
    private ProductRepository repository;
	
	@Override
	public void add(Product product) {
		repository.save(product);
	}

	@Override
	public Product findById(Long id) {
		try {
			Optional<Product> findById = repository.findById(id);
			return findById.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
