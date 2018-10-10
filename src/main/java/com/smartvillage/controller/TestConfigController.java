package com.smartvillage.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smartvillage.Result;
import com.smartvillage.dao.ProductRepository;
import com.smartvillage.entity.Product;
import com.smartvillage.service.ProductService;
import com.smartvillage.utils.ResultUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/app")
@Api(value="Test",tags="测试连通性")
public class TestConfigController {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "根据id获取产品信息", notes = "根据id获取产品信息", httpMethod = "GET", response = Product.class)
	public ResponseEntity<Product> get(@PathVariable Long id) {
		//Product findById = service.findById(id);
		Optional<Product> findById = productRepository.findById(id);
		return ResponseEntity.ok(findById.get());
	}

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "添加一个新的产品")
	public ResponseEntity<String> add(Product product) {
		//service.add(product);
		return ResponseEntity.ok("SUCCESS");
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "更新一个产品")
	public ResponseEntity<String> update(Product product) {
		return ResponseEntity.ok("SUCCESS");
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取所有产品信息", notes = "获取所有产品信息", httpMethod = "GET", response = Product.class, responseContainer = "List")
	public ResponseEntity<List<Product>> getAllProducts() {
		Product product = new Product();
		product.setName("七级滤芯净水器");
		product.setId(1L);
		product.setProductClass("seven_filters");
		product.setProductId("T12345");
		return ResponseEntity.ok(Arrays.asList(product, product));
	}
	@RequestMapping(value="list/{id}", method = RequestMethod.GET)
	@ApiOperation(value="异常测试")
    public Result list(@PathVariable Long id) {
        Product findByName = productService.findById(id);
        return ResultUtils.success(findByName);
    }
}
