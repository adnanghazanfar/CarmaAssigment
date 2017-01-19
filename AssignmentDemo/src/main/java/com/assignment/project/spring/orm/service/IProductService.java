package com.assignment.project.spring.orm.service;

import java.util.Collection;
import java.util.List;

import com.assignment.project.spring.orm.model.Product;

/**
 * Product service, for all Product related operations.
 * Transaction supported.
 * 
 * @author ADNAN GHAZANFAR
 * @email adnan.ghazanfar@yahoo.com
 * @since 19 JAN, 2017
 */
public interface IProductService {

	/**
	 * Save or Update existing product.
	 * 
	 * @param product
	 */
	void saveOrUpdate(Product product);

	/**
	 * Delete existing product.
	 * 
	 * @param product
	 */
	public void delete(Product product);

	/**
	 * Get all existing products.
	 * 
	 * @param product
	 * @return products
	 */
	public List<Product> findAll();

	/**
	 * Save or Update multitple products.
	 * 
	 * @param products
	 * 
	 */
	public void saveAll(Collection<Product> products);

	/**
	 * Find product By name.
	 * 
	 * @param name
	 * @return products
	 */
	public List<Product> findByNameIs(String name);

	public List<Product> findByNameContainingIgnoreCase(String searchString);
}
