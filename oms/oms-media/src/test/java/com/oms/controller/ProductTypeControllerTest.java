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
import com.oms.model.ProductType;
import com.oms.service.OMSUserService;
import com.oms.service.ProductTypeService;

/**
 * Tests the Product Type controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductTypeControllerTest {

	@InjectMocks
	ProductTypeController productTypeController;
	
	@Mock
	private ProductTypeService productTypeService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;
	
	private List<ProductType> productTypeLst;
	
	private ProductType productType;
	
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
		
		productTypeLst = new ArrayList<ProductType>();
		productType = new ProductType();
		productType.setTypeId(12345L);
		productTypeLst.add(productType);
    }
	
	/**
	 * Tests getAllProductType() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testGetAllProductType() throws IllegalAccessException, InvocationTargetException{
		when(productTypeService.getAllProductType()).thenReturn(productTypeLst);
		List<ProductType> list = productTypeController.getAllProductType();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests getProductType() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testGetProductType() throws IllegalAccessException, InvocationTargetException, OMSSystemException{
		when(productTypeService.getProductType(any(Long.class))).thenReturn(productType);
		ProductType type = productTypeController.getProductType(12345L);
		Assert.assertNotNull(type);
	}
	
	/**
	 * Tests deleteProductType() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testDeleteProductType() throws IllegalAccessException, InvocationTargetException, OMSSystemException{
		Mockito.doNothing().when(productTypeService).deleteProductType(any(Long.class));
	}
	
	/**
	 * Tests updateProductType() method.
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testUpdateProductType() throws IllegalAccessException, InvocationTargetException, Exception {
		when(productTypeService.updateProductType(any(ProductType.class))).thenReturn(productType);
		ProductType type = productTypeController.updateProductType(productType);
		Assert.assertNotNull(type);
	}
	
	/**
	 * Tests addNewProductType() method.
	 * @throws Exception  
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testAddNewProductType() throws IllegalAccessException, InvocationTargetException, Exception {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(productTypeService.addProductType(any(ProductType.class))).thenReturn(productType);
		ProductType type = productTypeController.addNewProductType(productType);
		Assert.assertNotNull(type);
	}
}
