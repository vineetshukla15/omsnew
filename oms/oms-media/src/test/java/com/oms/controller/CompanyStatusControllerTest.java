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

import com.oms.model.CompanyStatus;
import com.oms.service.CompanyStatusService;
import com.oms.service.OMSUserService;

/**
 * Tests the company status controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CompanyStatusControllerTest {
	
	@InjectMocks
	CompanyStatusController companyStatusController;
	
	@Mock
	private CompanyStatusService companyStatusService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;
	
	private List<CompanyStatus> companyStatusList;
	
	private CompanyStatus companyStatus;
	
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
		
		companyStatusList = new ArrayList<CompanyStatus>();
		companyStatus = new CompanyStatus();
		companyStatus.setDeleted(false);
		companyStatus.setCompanyStatusId(12345L);
		companyStatus.setName("ComStatus");
		companyStatusList.add(companyStatus);
    }
	
	/**
	 * Tests listCompaniesStatus() method.
	 */
	@Test
	public void testListCompaniesStatus(){
		when(companyStatusService.listCompaniesStatus()).thenReturn(companyStatusList);
		List<CompanyStatus> list = companyStatusController.listCompaniesStatus();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests addCompanyStatus() method.
	 */
	@Test
	public void testAddCompanyStatus() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(companyStatusService.addCompanyStatus(any(CompanyStatus.class))).thenReturn(companyStatus);
		CompanyStatus comp = companyStatusController.addCompanyStatus(companyStatus);
		Assert.assertNotNull(comp);
	}
	
	/**
	 * Tests updateCompanyStatus() method.
	 */
	@Test
	public void testUpdateCompanyStatus() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(companyStatusService.updateCompanyStatus(any(CompanyStatus.class))).thenReturn(companyStatus);
		CompanyStatus comp = companyStatusController.updateCompanyStatus(companyStatus);
		Assert.assertNotNull(comp);
	}
	
	/**
	 * Tests removeCompanyStatus() method.
	 */
	@Test
	public void testRemoveCompanyStatus(){
		Mockito.doNothing().when(companyStatusService).removeCompanyStatus(any(Long.class));
	}
	
	/**
	 * Tests getCompanyStatusByID() method.
	 */
	@Test
	public void testGetCompanyStatusByID(){
		when(companyStatusService.getCompanyStatusById(any(Long.class))).thenReturn(companyStatus);
		CompanyStatus comp = companyStatusController.getCompanyStatusByID(12345L);
		Assert.assertNotNull(comp);
	}

}
