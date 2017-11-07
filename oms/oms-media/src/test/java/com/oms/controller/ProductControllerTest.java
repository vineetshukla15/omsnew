/**
 * 
 */
package com.oms.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.tavant.api.auth.model.OMSUser;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Creative;
import com.oms.model.Product;
import com.oms.service.OMSUserService;
import com.oms.service.ProductService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

/**
 * Tests the creative controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

	@InjectMocks
	ProductController productController;

	@Mock
	private ProductService productService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;

	private List<Product> productLst;

	private Product product;
	
	OMSUser user;
	
	Authentication authentication;
	
	SecurityContext securityContext;
	
	/**
	 * Sets up the test data.
	 */
	@Before
	public void setUp() {
		authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.getPrincipal()).thenReturn("abc");
		securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		user = new OMSUser();
		user.setUsername("abc");
		user.setId(12345L);
		
		productLst = new ArrayList<Product>();
		product = new Product();
		product.setProductId(12345L);
		product.setName("P1");
		productLst.add(product);
	}
	
	/**
	 * Tests listProducts() method.
	 */
	@Test
	public void testListProducts() {
		when(productService.listProducts()).thenReturn(productLst);
		List<Product> list = productController.listProducts();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests getProductById() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testGetProductById() throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		when(productService.getProductById(any(Long.class))).thenReturn(product);
		Product prdct = productController.getProductById(12345L);
		Assert.assertNotNull(prdct);
	}
	
	/**
	 * Tests removeProduct() method.
	 */
	@Test
	public void testRemoveProduct() {
		Mockito.doNothing().when(productService).removeProduct(any(Long.class));
	}
	
	/**
	 * Tests searchProduct() method.
	 */
	@Test
	public void testSearchProduct() {
		SearchRequestVO searchRequest = new SearchRequestVO();
		searchRequest.setLength(10);
		searchRequest.setDraw(10);
		when(productService.searchProduct(any(SearchRequestVO.class))).thenReturn(new PaginationResponseVO<Product>());
		PaginationResponseVO<Product> prod = productController.searchProduct(searchRequest);
		Assert.assertNotNull(prod);
	}
	
	/**
	 * Tests updateProduct() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testUpdateProduct() throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(productService.updateProduct(any(Product.class))).thenReturn(product);
		Product prod = productController.updateProduct(product);
		Assert.assertNotNull(prod);
	}
	
	/**
	 * Tests addProduct() method.
	 */
	@Test
	public void testAddProduct() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(productService.addProduct(any(Product.class))).thenReturn(product);
		Product prod = productController.addProduct(product);
		Assert.assertNotNull(prod);
	}

}
