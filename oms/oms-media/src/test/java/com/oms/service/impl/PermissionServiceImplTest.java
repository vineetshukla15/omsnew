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
import org.tavant.api.auth.model.Permissions;

import com.oms.repository.PermissionRepository;

/**
 * Tests the Permission service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PermissionServiceImplTest {
	
	@InjectMocks
	PermissionServiceImpl permissionServiceImpl;
	
	@Mock
	private PermissionRepository permissionRepository;
	
	private List<Permissions> permissionsList;
	
	private Permissions permissions;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {		
		permissionsList = new ArrayList<Permissions>();
		permissions = new Permissions();
		permissions.setPermissionId(12345L);
		permissions.setDeleted(false);
		permissions.setPermissionName("P1");
		permissionsList.add(permissions);
    }

	/**
	 * Tests positive scenario for displayPermissions() method.
	 */
	@Test
	public void testDisplayPermissionsPositive() {
		when(permissionRepository.findByDeletedFalseOrderByPermissionName()).thenReturn(permissionsList);
		List<Permissions> list = permissionServiceImpl.displayPermissions();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
}
