/**
 * 
 */
package com.oms.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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

import com.oms.model.Contact;
import com.oms.model.LineItems;
import com.oms.service.LineItemsService;
import com.oms.service.OMSUserService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

/**
 * Tests the Line Items controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LineItemsControllerTest {

	@InjectMocks
	LineItemsController lineItemsController;
	
	@Mock
	private LineItemsService lineItemsService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;
	
	private List<LineItems> lineItemsLst;
	
	private LineItems lineItems;
	
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
		
		lineItemsLst = new ArrayList<LineItems>();
		lineItems = new LineItems();
		lineItems.setLineItemId(12345L);
		lineItemsLst.add(lineItems);
    }
	
	/**
	 * Tests listLineItems() method.
	 */
	@Test
	public void testListLineItems(){
		when(lineItemsService.getAllLineItems()).thenReturn(lineItemsLst);
		List<LineItems> list = lineItemsController.listLineItems();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests updateLineItem() method.
	 */
	@Test
	public void testUpdateLineItem() {
		when(lineItemsService.updateLineItems(any(LineItems.class))).thenReturn(lineItems);
		LineItems items = lineItemsController.updateLineItem(lineItems);
		Assert.assertNotNull(items);
	}
	
	/**
	 * Tests addLineItem() method.
	 */
	@Test
	public void testAddLineItem() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(lineItemsService.addLineItem(any(LineItems.class))).thenReturn(lineItems);
		LineItems items = lineItemsController.addLineItem(lineItems);
		Assert.assertNotNull(items);
	}
	
	/**
	 * Tests getLineItemsById() method.
	 */
	@Test
	public void testGetLineItemsById(){
		when(lineItemsService.getLineItemById(any(Long.class))).thenReturn(lineItems);
		LineItems items = lineItemsController.getLineItemsById(12345L);
		Assert.assertNotNull(items);
	}
	
	/**
	 * Tests removeLineItems() method.
	 */
	@Test
	public void testRemoveLineItems(){
		Mockito.doNothing().when(lineItemsService).removeLineItems(any(Long.class));
	}
	
	/**
	 * Tests searchLineItems() method.
	 */
	@Test
	public void testsearchLineItems() {
/*		SearchRequestVO searchRequest = new SearchRequestVO();
		searchRequest.setLength(10);
		searchRequest.setDraw(10);
		when(lineItemsService.searchLineItems(any(SearchRequestVO.class))).thenReturn(new PaginationResponseVO<LineItems>());
		PaginationResponseVO<LineItems> items = lineItemsController.searchLineItems(searchRequest);   
		Assert.assertNotNull(items);*/
	}
	
	/**
	 * Tests getPrice() method.
	 */
	@Test
	public void testGetPrice(){
		when(lineItemsService.getPrice(any(LineItems.class))).thenReturn(new BigDecimal(10));
		String cost = lineItemsController.getPrice(lineItems);
		Assert.assertEquals("10", cost);
	}
}
