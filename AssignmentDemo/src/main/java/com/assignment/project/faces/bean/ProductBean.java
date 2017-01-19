package com.assignment.project.faces.bean;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.assignment.project.spring.orm.model.Product;
import com.assignment.project.spring.orm.service.IProductService;

/**
 * This class is a controller, for all Product related UI operation. THis is
 * assigment so I am not adding validation/ error handling.
 * 
 * @author ADNAN GHAZANFAR
 * @email adnan.ghazanfar@yahoo.com
 * @since 19 JAN, 2017
 */

@ManagedBean
@SessionScoped
public class ProductBean {

	private Product product;
	private List<Product> productList;

	@ManagedProperty("#{productService}")
	private IProductService productService;

	@PostConstruct
	private void defaultProducts() {
		List<Product> productList = Arrays.asList(new Product("P1", "Television"), new Product("P2", "Phone"),
				new Product("P3", "Peach", 5.0, 1000), new Product("P4", "Strawberry"),
				new Product("P5", "Melone", 10.8), new Product("P6", "Onion", 2000.0, 450));
		productService.saveAll(productList);
	}

	public void initValues() {
		product = new Product();
		retrieve();
	}

	/**
	 * Save new product. For POC I am not adding any logic to check whether
	 * product with same name already exists or not.
	 */
	public void save() {
		if (product != null) {
			productService.saveOrUpdate(product);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Saved Successfully"));
			initValues();
		}
	}

	/**
	 * Retrieve all saved products. Can add pagination in case data increases in
	 * realtime application.
	 */
	public void retrieve() {
		productList = productService.findAll();
	}

	/**
	 * Update existing product.
	 * 
	 * @param product
	 */
	public void update(RowEditEvent event) {
		Product product = ((Product) event.getObject());
		if (product != null) {
			productService.saveOrUpdate(product);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Updated Successfully"));
			initValues();
		}
	}

	/**
	 * Delete existing product.
	 * 
	 * @param product
	 */
	public void delete(Product product) {
		if (product != null) {
			productService.delete(product);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product deleted successfully."));
			initValues();
		}
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

}
