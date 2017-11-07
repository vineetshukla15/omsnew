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
import org.tavant.api.auth.model.HighLevelPermission;

import com.oms.model.BillingSource;
import com.oms.repository.HighLevelPermissionRepository;

/**
 * Tests the High Level Permission service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HighLevelPermissionServiceImplTest {

	@InjectMocks
	HighLevelPermissionServiceImpl highLevelPermissionServiceImpl;
	
	@Mock
	private HighLevelPermissionRepository highLevelPermissionRepository;
	
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
		highLevelPermission.setDeleted(false);
		highLevelPermission.setPermissionName("P1");
		highLevelPermissionLst.add(highLevelPermission);
    }
	
	/**
	 * Tests positive scenario for displayHighLevelPermissions() method.
	 */
	@Test
	public void testDisplayHighLevelPermissionsPositive() {
		when(highLevelPermissionRepository.findByDeletedFalseOrderByPermissionNameAsc()).thenReturn(highLevelPermissionLst);
		List<HighLevelPermission> list = highLevelPermissionServiceImpl.displayHighLevelPermissions();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
}
