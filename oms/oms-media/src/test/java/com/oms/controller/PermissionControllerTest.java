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
import org.tavant.api.auth.model.Permissions;

import com.oms.service.PermissionService;

/**
 * Tests the Permission controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PermissionControllerTest {

	@InjectMocks
	PermissionController permissionController;
	
	@Mock
	private PermissionService permissionService;
	
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
		permissionsList.add(permissions);
    }
	
	/**
	 * Tests displayPermissions() method.
	 */
	@Test
	public void testDisplayPermissions(){
		when(permissionService.displayPermissions()).thenReturn(permissionsList);
		List<Permissions> list = permissionController.displayPermissions();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
}
