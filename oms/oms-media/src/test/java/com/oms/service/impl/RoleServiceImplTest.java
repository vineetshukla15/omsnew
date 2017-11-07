/**
 * 
 */
package com.oms.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

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
import org.tavant.api.auth.model.Role;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Product;
import com.oms.repository.RoleRepository;

/**
 * Tests the product service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

	@InjectMocks
	RoleServiceImpl roleServiceImpl;
	
	@Mock
	private RoleRepository roleRepository;
	
	private List<Role> roleList;
	
	private Role role;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		roleList = new ArrayList<Role>();
		role = new Role();
		role.setRoleId(12345L);
		role.setRoleName("Admin");
		role.setDeleted(false);
		roleList.add(role);
    }
	
	/**
	 * Tests positive scenario for addRole() method.
	 */
	@Test
	public void testAddRolePositive() {
		when(roleRepository.findRoleByName(any(String.class), any(Boolean.class))).thenReturn(null);
		when(roleRepository.save(any(Role.class))).thenReturn(role);
		Role roles = roleServiceImpl.addRole(role);
		Assert.assertNotNull(roles);
	}
	
	/**
	 * Tests negative scenario for addRole() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testAddRoleNegative() {
		when(roleRepository.findRoleByName(any(String.class), any(Boolean.class))).thenReturn(role);
		Role roles = roleServiceImpl.addRole(role);
	}
	
	/**
	 * Tests negative scenario for updateRole() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateRoleNegative() {
		role.setRoleId(null);
		Role roles = roleServiceImpl.updateRole(role);
	}
	
	/**
	 * Tests positive scenario for updateRole() method.
	 */
	@Test
	public void testUpdateRolePositive() {
		when(roleRepository.save(any(Role.class))).thenReturn(role);
		Role roles = roleServiceImpl.updateRole(role);
		Assert.assertNotNull(roles);
	}
	
	/**
	 * Tests positive scenario for listRoles() method.
	 */
	@Test
	public void testListRolesPositive() {
		when(roleRepository.findByDeletedFalseOrderByRoleNameAsc()).thenReturn(roleList);
		List<Role> list = roleServiceImpl.listRoles();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests positive scenario for getRoleById() method.
	 */
	@Test
	public void testGetRoleByIdPositive() {
		when(roleRepository.findById(any(Long.class))).thenReturn(role);
		Role roles = roleServiceImpl.getRoleById(12345L);
		Assert.assertNotNull(roles);
	}
	
	/**
	 * Tests negative scenario for getRoleById() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetRoleByIdNegative() {
		when(roleRepository.findById(any(Long.class))).thenReturn(null);
		Role roles = roleServiceImpl.getRoleById(12345L);
	}
	
	/**
	 * Tests negative scenario for removeRole() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testRemoveProductNegative() {
		role.setDeleted(true);
		when(roleRepository.findById(any(Long.class))).thenReturn(role);
		roleServiceImpl.removeRole(12345L);
	}
	
	/**
	 * Tests positive scenario for removeRole() method.
	 */
	@Test
	public void testRemoveProductPositive() {
		when(roleRepository.findById(any(Long.class))).thenReturn(role);
		Mockito.doNothing().when(roleRepository).softDelete(any(Long.class));
		roleServiceImpl.removeRole(12345L);
	}
}
