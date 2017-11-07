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
import com.oms.model.SpecType;
import com.oms.service.SpecTypeService;

/**
 * Tests the SpecType controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SpecTypeControllerTest {

	@InjectMocks
	SpecTypeController specTypeController;
	
	@Mock
	private SpecTypeService specTypeService;
	
	private List<SpecType> specTypeList;
	
	private SpecType specType;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {		
		specTypeList = new ArrayList<SpecType>();
		specType = new SpecType();
		specType.setSpec_Id(12345L);
		specTypeList.add(specType);
    }
	
	/**
	 * Tests listSpecTypes() method.
	 */
	@Test
	public void testListSpecTypes(){
		when(specTypeService.listSpecType()).thenReturn(specTypeList);
		List<SpecType> list = specTypeController.listSpecTypes();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
}
