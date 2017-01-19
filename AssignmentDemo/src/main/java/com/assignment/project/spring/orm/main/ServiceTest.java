package com.assignment.project.spring.orm.main;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.assignment.project.spring.orm.model.Product;
import com.assignment.project.spring.orm.service.IProductService;

public class ServiceTest {
	public static void main(String[] args) {

		// Create Spring application context
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");

		// Get service from context.
		IProductService productService = ctx.getBean(IProductService.class);

		// Add some items
		List<Product> productList = Arrays.asList(new Product("P1", "Television"), new Product("P2", "Phone"),
				new Product("P3", "Peach", 5.0, 1000), new Product("P4", "Strawberry"),
				new Product("P5", "Melone", 10.8), new Product("P6", "Onion", 2000.0, 450));
		productService.saveAll(productList);
		
		// test update
		productList = productService.findByNameIs("Phone");
		productService.saveAll(productList);
		productList = productService.findAll();

		ctx.close();
	}
}
