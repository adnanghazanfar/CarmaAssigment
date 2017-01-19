package com.assignment.project.spring.orm.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.project.spring.orm.model.Product;
import com.assignment.project.spring.orm.repository.ProductRepository;
import com.assignment.project.spring.orm.service.IProductService;

/** 
 * Service layer.
 * Specify transactional behavior and mainly
 * delegate calls to Repository.
 */
@Service("productService")
public class ProductService implements IProductService{

	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public void saveOrUpdate(Product product) {
		productRepository.save(product);
	}
	
	@Transactional
	public void delete(Product product) {
		productRepository.delete(product);
	}
	
	@Transactional(readOnly=true)
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Transactional
	public void saveAll(Collection<Product> products) {
		for (Product product : products) {
			productRepository.save(product);
		}
	}

	@Transactional(readOnly=true)
	public List<Product> findByNameIs(String name) {
		return productRepository.findByNameIs(name);
	}
	
	@Transactional(readOnly=true)
	public List<Product> findByNameContainingIgnoreCase(String searchString) {
		return productRepository.findByNameContainingIgnoreCase(searchString);
	}
}
