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

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Premium;
import com.oms.service.OMSUserService;
import com.oms.service.PremiumService;

/**
 * Tests the Premium controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PremiumControllerTest {

	@InjectMocks
	PremiumController premiumController;
	
	@Mock
	private PremiumService premiumService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;
	
	private List<Premium> premiumLst;
	
	private Premium premium;
	
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
		
		premiumLst = new ArrayList<Premium>();
		premium = new Premium();
		premium.setPremiumId(12345L);
		premiumLst.add(premium);
    }
	
	/**
	 * Tests getAllPremium() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testGetAllPremium() throws IllegalAccessException, InvocationTargetException{
		when(premiumService.getAllPremium()).thenReturn(premiumLst);
		List<Premium> list = premiumController.getAllPremium();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests getPremium() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testGetPremium() throws IllegalAccessException, InvocationTargetException, OMSSystemException{
		when(premiumService.getPremium(any(Long.class))).thenReturn(premium);
		Premium premiums = premiumController.getPremium(12345L);
		Assert.assertNotNull(premiums);
	}
	
	/**
	 * Tests deletePremium() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testDeletePremium() throws IllegalAccessException, InvocationTargetException, OMSSystemException{
		Mockito.doNothing().when(premiumService).deletePremium(any(Long.class));
	}
	
	/**
	 * Tests updatePremium() method.
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testUpdatePremium() throws IllegalAccessException, InvocationTargetException, Exception {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(premiumService.updatePremium(any(Premium.class))).thenReturn(premium);
		Premium premiums = premiumController.updatePremium(premium);
		Assert.assertNotNull(premiums);
	}
	
	/**
	 * Tests addPremium() method.
	 * @throws Exception 
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testAddPremium() throws IllegalAccessException, InvocationTargetException, OMSSystemException, Exception {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(premiumService.addPremium(any(Premium.class))).thenReturn(premium);
		Premium premiums = premiumController.addPremium(premium);
		Assert.assertNotNull(premiums);
	}
}
