/**
 * 
 */
package com.oms.controller;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.tavant.api.auth.model.OMSUser;
import org.tavant.api.auth.model.Role;

import com.oms.service.OMSUserService;
import com.oms.service.RoleService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

/**
 * Tests the creative controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RoleControllerTest {

	@InjectMocks
	RoleController roleController;

	@Mock
	private RoleService roleService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;

	private List<Role> roleLst;

	private Role role;
	
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
		
		roleLst = new ArrayList<Role>();
		role = new Role();
		role.setRoleId(12345L);
		role.setRoleName("Admin");
		roleLst.add(role);
	}
	
	/**
	 * Tests listRoles() method.
	 */
	@Test
	public void testListRoles() {
		when(roleService.listRoles()).thenReturn(roleLst);
		List<Role> list = roleController.listRoles();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests removeRole() method.
	 */
	@Test
	public void testRemoveRole() {
		Mockito.doNothing().when(roleService).removeRole(any(Long.class));
	}
	
	/**
	 * Tests getRole() method.
	 */
	@Test
	public void testGetRole(){
		when(roleService.getRoleById(any(Long.class))).thenReturn(role);
		Role rol = roleController.getRole(12345L);
		Assert.assertNotNull(rol);
	}
	
	/**
	 * Tests searchUser() method.
	 */
	@Test
	public void testSearchUser() {
		SearchRequestVO searchRequest = new SearchRequestVO();
		searchRequest.setLength(10);
		searchRequest.setDraw(10);
		when(roleService.searchRole(any(SearchRequestVO.class))).thenReturn(new PaginationResponseVO<Role>());
		PaginationResponseVO<Role> role = roleController.searchUser(searchRequest);
		Assert.assertNotNull(role);
	}
	
	/**
	 * Tests updateRole() method.
	 */
	@Test
	public void testUpdateRole() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(roleService.updateRole(any(Role.class))).thenReturn(role);
		Role roles = roleController.updateRole(role);
		Assert.assertNotNull(roles);
	}
	
	/**
	 * Tests addRole() method.
	 */
	@Test
	public void testAddRole() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(roleService.addRole(any(Role.class))).thenReturn(role);
		Role roles = roleController.addRole(role);
		Assert.assertNotNull(roles);
	}
}
