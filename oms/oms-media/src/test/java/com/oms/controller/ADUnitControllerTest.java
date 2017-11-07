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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.tavant.api.auth.model.OMSUser;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.ADUnit;
import com.oms.service.ADUnitService;
import com.oms.service.OMSUserService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

/**
 * Tests the ADUnit controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ADUnitControllerTest {
	
	@InjectMocks
	ADUnitController aDUnitController;
	
	@Mock
	private ADUnitService adUnitService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;
	
	private List<ADUnit> aDUnitLst;
	
	private ADUnit aDUnit;
	
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
		
		aDUnitLst = new ArrayList<ADUnit>();
		aDUnit = new ADUnit();
		aDUnit.setAdUnitId(12345L);
		aDUnit.setName("Unit1");
		aDUnitLst.add(aDUnit);
    }
	
	/**
	 * Tests getAllADUnit() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testGetAllADUnit() throws IllegalAccessException, InvocationTargetException{
		when(adUnitService.getAllADUnit()).thenReturn(aDUnitLst);
		List<ADUnit> list = aDUnitController.getAllADUnit();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests getADUnit() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws OMSSystemException 
	 */
	@Test
	public void testGetADUnit() throws OMSSystemException, IllegalAccessException, InvocationTargetException{
		when(adUnitService.getADUnit(any(Long.class))).thenReturn(aDUnit);
		ResponseEntity<ADUnit> unit = aDUnitController.getADUnit(12345L);
		Assert.assertNotNull(unit);
	}
	
	/**
	 * Tests updateADUnit() method.
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testUpdateADUnit() throws IllegalAccessException, InvocationTargetException, Exception {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(adUnitService.updateADUnit(any(ADUnit.class))).thenReturn(aDUnit);
		ADUnit unit = aDUnitController.updateADUnit(aDUnit);
		Assert.assertNotNull(unit);
	}
	
	/**
	 * Tests addNewADUnit() method.
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testAddNewADUnit() throws IllegalAccessException, InvocationTargetException, Exception {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(adUnitService.addADUnit(any(ADUnit.class))).thenReturn(aDUnit);
		ADUnit unit = aDUnitController.addNewADUnit(aDUnit);
		Assert.assertNotNull(unit);
	}
	
	/**
	 * Tests deleteADUnit() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testDeleteADUnit() throws IllegalAccessException, InvocationTargetException, OMSSystemException{
		Mockito.doNothing().when(adUnitService).deleteADUnit(any(Long.class));
	}
	
	/**
	 * Tests searchADUnit() method.
	 */
	@Test
	public void testSearchADUnit() {
		SearchRequestVO searchRequest = new SearchRequestVO();
		searchRequest.setLength(10);
		searchRequest.setDraw(10);
		when(adUnitService.searchAdUnit(any(SearchRequestVO.class))).thenReturn(new PaginationResponseVO<ADUnit>());
		PaginationResponseVO<ADUnit> cntct = aDUnitController.searchADUnit(searchRequest);
		Assert.assertNotNull(cntct);
	}

}
