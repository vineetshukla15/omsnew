/**
 * 
 */
package com.oms.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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

import com.oms.model.Creative;
import com.oms.service.CreativeService;
import com.oms.service.OMSUserService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

/**
 * Tests the creative controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CreativeControllerTest {

	@InjectMocks
	CreativeController creativeController;

	@Mock
	private CreativeService creativeService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;

	private List<Creative> creativeLst;

	private Creative creative;
	
	OMSUser user;
	
	Authentication authentication;
	
	SecurityContext securityContext;

	/**
	 * Tests getCreative() method.
	 */
	@Test
	public void testGetCreative() {
		when(creativeService.getAllCreatives()).thenReturn(creativeLst);
		List<Creative> list = creativeController.getCreative();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * Tests getCreativesByName() method.
	 */
	@Test
	public void testGetCreativesByName() {
		when(creativeService.getCreativesByName(any(String.class))).thenReturn(creativeLst);
		List<Creative> list = creativeController.getCreativesByName("Name1");
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * Tests getCreativeById() method.
	 */
	@Test
	public void testGetCreativeById() {
		when(creativeService.getCreativeById(any(Long.class))).thenReturn(creative);
		Creative creatve = creativeController.getCreativeById(12345L);
		Assert.assertNotNull(creatve);
	}

	/**
	 * Tests removeCreative() method.
	 */
	@Test
	public void testRemoveCreative() {
		Mockito.doNothing().when(creativeService).deleteCreative(any(Long.class));
	}

	/**
	 * Tests updateCreative() method.
	 */
	@Test
	public void testUpdateCreative() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(creativeService.updateCreative(any(Creative.class))).thenReturn(creative);
		Creative creatv = creativeController.updateCreative(creative);
		Assert.assertNotNull(creatv);
	}
	
	/**
	 * Tests addNewCreative() method.
	 */
	@Test
	public void testAddNewCreative() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(creativeService.addCreative(any(Creative.class))).thenReturn(creative);
		Creative creatv = creativeController.addNewCreative(creative);
		Assert.assertNotNull(creatv);
	}
	
	/**
	 * Tests searchCreatives() method.
	 */
	@Test
	public void testSearchCreatives() {
		SearchRequestVO searchRequest = new SearchRequestVO();
		searchRequest.setLength(10);
		searchRequest.setDraw(10);
		when(creativeService.searchCreatives(any(SearchRequestVO.class))).thenReturn(new PaginationResponseVO<Creative>());
		PaginationResponseVO<Creative> creatve = creativeController.searchCreatives(searchRequest);
		Assert.assertNotNull(creatve);
	}

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
		
		creativeLst = new ArrayList<Creative>();
		creative = new Creative();
		creative.setCreativeId(12345L);
		creative.setHeight1(10.0);
		creative.setWidth1(5.0);
		creative.setHeight2(20.0);
		creative.setWidth2(15.0);
		creative.setName("Name1");
		creative.setDescription("Description1");
		creativeLst.add(creative);
	}

}
