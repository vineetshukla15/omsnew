package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.oms.model.Product;

public interface ProductRepository extends SoftDeleteRepository<Product, Long>, JpaSpecificationExecutor<Product>,
		PagingAndSortingRepository<Product, Long> {

	List<Product> findByDeletedFalseOrderByName();

	@Query("SELECT p from Product p where p.deleted= :deleted and lower(p.name)=:name")
	Product findByName(@Param("name") String name, @Param("deleted") boolean deleted);

}
