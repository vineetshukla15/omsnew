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
import org.tavant.api.auth.model.HighLevelPermission;

import com.oms.model.BillingSource;
import com.oms.service.HighLevelPermissionService;

/**
 * Tests the High Level Permission controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HighLevelPermissionControllerTest {
	
	@InjectMocks
	HighLevelPermissionController highLevelPermissionController;
	
	@Mock
	private HighLevelPermissionService highLevelPermissionService;
	
	private List<HighLevelPermission> highLevelPermissionLst;
	
	private HighLevelPermission highLevelPermission;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {		
		highLevelPermissionLst = new ArrayList<HighLevelPermission>();
		highLevelPermission = new HighLevelPermission();
		highLevelPermission.setPermissionComponentId(12345L);
		highLevelPermissionLst.add(highLevelPermission);
    }

	/**
	 * Tests displayHighLevelPermissions() method.
	 */
	@Test
	public void testDisplayHighLevelPermissions(){
		when(highLevelPermissionService.displayHighLevelPermissions()).thenReturn(highLevelPermissionLst);
		List<HighLevelPermission> list = highLevelPermissionController.displayHighLevelPermissions();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
}
