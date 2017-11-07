/**
 * 
 */
package com.oms.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Contact;
import com.oms.model.Product;
import com.oms.repository.ProductRepository;

/**
 * Tests the product service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

	@InjectMocks
	ProductServiceImpl productServiceImpl;
	
	@Mock
	private ProductRepository productRepository;
	
	private List<Product> productList;
	
	private Product product;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		productList = new ArrayList<Product>();
		product = new Product();
		product.setProductId(12345L);
		product.setName("P1");
		product.setDeleted(false);
		productList.add(product);
    }
	
	/**
	 * Tests positive scenario for listProducts() method.
	 */
	@Test
	public void testListProductsPositive() {
		when(productRepository.findByDeletedFalseOrderByName()).thenReturn(productList);
		List<Product> list = productServiceImpl.listProducts();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests positive scenario for getProductById() method.
	 */
	@Test
	public void testGetProductByIdPositive() {
		when(productRepository.findById(any(Long.class))).thenReturn(product);
		Product prod = productServiceImpl.getProductById(12345L);
		Assert.assertNotNull(prod);
	}
	
	/**
	 * Tests negative scenario for getProductById() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetProductByIdNegative() {
		when(productRepository.findById(any(Long.class))).thenReturn(null);
		Product prod = productServiceImpl.getProductById(12345L);
	}
	
	/**
	 * Tests negative scenario for removeProduct() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testRemoveProductNegative() {
		when(productRepository.findById(any(Long.class))).thenReturn(null);
		productServiceImpl.removeProduct(12345L);
	}
	
	/**
	 * Tests positive scenario for removeProduct() method.
	 */
	@Test
	public void testRemoveProductPositive() {
		when(productRepository.findById(any(Long.class))).thenReturn(product);
		Mockito.doNothing().when(productRepository).softDelete(any(Long.class));
		productServiceImpl.removeProduct(12345L);
	}
	
	/**
	 * Tests positive scenario for addProduct() method.
	 */
	@Test
	public void testAddProductPositive() {
		when(productRepository.findByName(any(String.class), any(Boolean.class))).thenReturn(null);
		when(productRepository.save(any(Product.class))).thenReturn(product);
		Product prod = productServiceImpl.addProduct(product);
		Assert.assertNotNull(prod);
	}
	
	/**
	 * Tests negative scenario for addProduct() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testAddProductNegative() {
		when(productRepository.findByName(any(String.class), any(Boolean.class))).thenReturn(product);
		Product prod = productServiceImpl.addProduct(product);
	}
	
	/**
	 * Tests negative scenario for updateProduct() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateProductNegative() {
		product.setProductId(null);
		Product prod = productServiceImpl.updateProduct(product);
	}
	
	/**
	 * Tests positive scenario for updateProduct() method.
	 */
	@Test
	public void testUpdateProductPositive() {
		when(productRepository.save(any(Product.class))).thenReturn(product);
		Product prod = productServiceImpl.updateProduct(product);
		Assert.assertNotNull(prod);
	}
}
