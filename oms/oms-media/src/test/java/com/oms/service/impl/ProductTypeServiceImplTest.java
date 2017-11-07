/**
 * 
 */
package com.oms.service.impl;

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

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Premium;
import com.oms.model.ProductType;
import com.oms.repository.ProductTypeRepository;

/**
 * Tests the Product Type service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductTypeServiceImplTest {
	
	@InjectMocks
	ProductTypeServiceImpl productTypeServiceImpl;
	
	@Mock
	private ProductTypeRepository productTypeRepository;
	
	private List<ProductType> productTypeLst;
	
	private ProductType productType;

	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		productTypeLst = new ArrayList<ProductType>();
		productType = new ProductType();
		productType.setTypeId(12345L);
		productTypeLst.add(productType);
    }
	
	/**
	 * Tests positive scenario for getAllProductType() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testgGetAllProductTypePositive() throws IllegalAccessException, InvocationTargetException {
		when(productTypeRepository.findAll()).thenReturn(productTypeLst);
		List<ProductType> list = productTypeServiceImpl.getAllProductType();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests positive scenario for getProductType() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws OMSSystemException 
	 */
	@Test
	public void testGetProductTypePositive() throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		when(productTypeRepository.findById(any(Long.class))).thenReturn(productType);
		ProductType type = productTypeServiceImpl.getProductType(12345L);
		Assert.assertNotNull(type);
	}
	
	/**
	 * Tests negative scenario for getProductType() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws OMSSystemException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetProductTypeNegative() throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		when(productTypeRepository.findById(any(Long.class))).thenReturn(null);
		ProductType type = productTypeServiceImpl.getProductType(12345L);
	}
	
	/**
	 * Tests positive scenario for addProductType() method.
	 * @throws Exception 
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testAddProductTypePositive() throws IllegalAccessException, InvocationTargetException, Exception {
		when(productTypeRepository.save(any(ProductType.class))).thenReturn(productType);
		ProductType type = productTypeServiceImpl.addProductType(productType);
		Assert.assertNotNull(type);
	}
	
	/**
	 * Tests negative scenario for updateProductType() method.
	 * @throws Exception 
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateProductTypeNegative() throws IllegalAccessException, InvocationTargetException, OMSSystemException, Exception {
		productType.setTypeId(null);
		ProductType type = productTypeServiceImpl.updateProductType(productType);
	}
	
	/**
	 * Tests positive scenario for updateProductType() method.
	 * @throws Exception 
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testUpdateProductTypePositive() throws IllegalAccessException, InvocationTargetException, OMSSystemException, Exception {
		when(productTypeRepository.save(any(ProductType.class))).thenReturn(productType);
		ProductType type = productTypeServiceImpl.updateProductType(productType);
		Assert.assertNotNull(type);
	}
	
	/**
	 * Tests negative scenario for deleteProductType() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testDeleteProductTypeNegative() throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		when(productTypeRepository.findById(any(Long.class))).thenReturn(null);
		productTypeServiceImpl.deleteProductType(12345L);
	}
	
	/**
	 * Tests positive scenario for deleteProductType() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testDeleteProductTypePositive() throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		when(productTypeRepository.findById(any(Long.class))).thenReturn(productType);
		Mockito.doNothing().when(productTypeRepository).softDelete(any(Long.class));
		productTypeServiceImpl.deleteProductType(12345L);
	}
}
