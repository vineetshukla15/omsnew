/**
 * 
 */
package com.oms.controller;

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
import com.oms.model.SpecType;
import com.oms.service.SalesCategoryService;

/**
 * Tests the Sales Category controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SalesCategoryControllerTest {

	@InjectMocks
	SalesCategoryController salesCategoryController;
	
	@Mock
	private SalesCategoryService salesCategoryService;
	
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
		salesCategoryList.add(salesCategory);
    }
	
	/**
	 * Tests getSalesCategory() method.
	 */
	@Test
	public void testGetSalesCategory(){
		when(salesCategoryService.getAllSalesCategory()).thenReturn(salesCategoryList);
		List<SalesCategory> list = salesCategoryController.getSalesCategory();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
}
