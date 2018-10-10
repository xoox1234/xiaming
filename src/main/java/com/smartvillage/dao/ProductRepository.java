package com.smartvillage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartvillage.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
	Product findByName(String s);
}
