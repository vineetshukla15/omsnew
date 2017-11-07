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
import com.oms.model.Contact;
import com.oms.model.SeasonalDiscount;
import com.oms.service.ContactService;
import com.oms.service.OMSUserService;
import com.oms.service.SeasonalDiscountService;

/**
 * Tests the Seasonal Discount controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SeasonalDiscountControllerTest {

	@InjectMocks
	SeasonalDiscountController seasonalDiscountController;
	
	@Mock
	private SeasonalDiscountService seasonalDiscountService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;
	
	private List<SeasonalDiscount> seasonalDiscountLst;
	
	private SeasonalDiscount seasonalDiscount;
	
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
		
		seasonalDiscountLst = new ArrayList<SeasonalDiscount>();
		seasonalDiscount = new SeasonalDiscount();
		seasonalDiscount.setId(12345L);
		seasonalDiscountLst.add(seasonalDiscount);
    }
	
	/**
	 * Tests getAllSeasonalDiscount() method.
	 */
	@Test
	public void testGetAllSeasonalDiscount(){
		when(seasonalDiscountService.getAllSeasonalDiscount()).thenReturn(seasonalDiscountLst);
		List<SeasonalDiscount> list = seasonalDiscountController.getAllSeasonalDiscount();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests getSeasonalDiscount() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testGetSeasonalDiscount() throws IllegalAccessException, InvocationTargetException, OMSSystemException{
		when(seasonalDiscountService.getSeasonalDiscount(any(Long.class))).thenReturn(seasonalDiscount);
		SeasonalDiscount discount = seasonalDiscountController.getSeasonalDiscount(12345L);
		Assert.assertNotNull(discount);
	}
	
	/**
	 * Tests updateContact() method.
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testUpdateContact() throws IllegalAccessException, InvocationTargetException, Exception {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(seasonalDiscountService.updateSeasonalDiscount(any(SeasonalDiscount.class))).thenReturn(seasonalDiscount);
		SeasonalDiscount discount = seasonalDiscountController.updateSeasonalDiscount(seasonalDiscount);
		Assert.assertNotNull(discount);
	}
	
	/**
	 * Tests addSeasonalDiscount() method.
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testAddSeasonalDiscount() throws IllegalAccessException, InvocationTargetException, Exception {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(seasonalDiscountService.addSeasonalDiscount(any(SeasonalDiscount.class))).thenReturn(seasonalDiscount);
		SeasonalDiscount discount = seasonalDiscountController.addSeasonalDiscount(seasonalDiscount);
		Assert.assertNotNull(discount);
	}
	
	/**
	 * Tests deleteSeasonalDiscount() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testDeleteSeasonalDiscount() throws IllegalAccessException, InvocationTargetException, OMSSystemException{
		Mockito.doNothing().when(seasonalDiscountService).deleteSeasonalDiscount(any(Long.class));
	}
}
