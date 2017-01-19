package com.assignment.project.spring.orm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assignment.project.spring.orm.model.Product;

/** 
 * This is the Data Access layer.
 * PLease note that no need for any dao implementation. This is an interface!
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	/** spring-jpa-data understands this method name,
	 *  because it supports the resolution of specific keywords inside method names. **/
	public List<Product> findByNameContainingIgnoreCase(String searchString);
	
	
    /** You can define a JPA query.**/
	@Query("select p from Product p where p.name = :name")
    public List<Product> findByNameIs(@Param("name") String name);
    
}
