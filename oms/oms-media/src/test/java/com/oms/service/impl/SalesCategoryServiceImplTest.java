/**
 * 
 */
package com.oms.service.impl;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.oms.model.SalesCategory;
import com.oms.repository.SalesCategoryRepository;

/**
 * Tests the SalesCategory service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SalesCategoryServiceImplTest {

	@InjectMocks
	SalesCategoryServiceImpl salesCategoryServiceImpl;
	
	@Mock
	private SalesCategoryRepository salesCategoryRepository;
	
	private List<SalesCategory> salesCategoryList;
	
	private SalesCategory salesCategory;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {		
		salesCategoryList = new ArrayList<SalesCategory>();
		salesCategory = new SalesCategory();
		salesCategory.setSalesCatagoryId(12345L);
		salesCategory.setDeleted(false);
		salesCategoryList.add(salesCategory);
    }
	
	/**
	 * Tests positive scenario for getAllSalesCategory() method.
	 */
	@Test
	public void testGetAllSalesCategoryPositive() {
		when(salesCategoryRepository.findByDeletedFalseOrderByNameAsc()).thenReturn(salesCategoryList);
		List<SalesCategory> list = salesCategoryServiceImpl.getAllSalesCategory();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
}
