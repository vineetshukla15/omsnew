/**
 * 
 */
package com.oms.controller;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.tavant.api.auth.model.OMSUser;

import com.oms.model.NewCreative;
import com.oms.service.NewCreativeService;
import com.oms.service.OMSUserService;

/**
 * Tests the Proposal controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class NewCreativeControllerTest {
	
	@InjectMocks
	NewCreativeController newCreativeController;
	
	@Mock
	private NewCreativeService newCreativeService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;
	
	private List<NewCreative> creativeList;
	
	private NewCreative newCreative;
	
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
		
		creativeList = new ArrayList<NewCreative>();
		newCreative = new NewCreative();
		newCreative.setNewCreativeId(12345L);
		creativeList.add(newCreative);
    }
	
	
	
	/**
	 * Tests addNewCreative() method.
	 */
	@Test
	public void testAddNewCreative() throws IllegalAccessException, InvocationTargetException, Exception{
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(newCreativeService.addCreative(any(NewCreative.class), any(Long.class))).thenReturn(newCreative);
		NewCreative prop = newCreativeController.addNewCreative(newCreative);
		Assert.assertNotNull(prop);
		
	}
	
	
}
