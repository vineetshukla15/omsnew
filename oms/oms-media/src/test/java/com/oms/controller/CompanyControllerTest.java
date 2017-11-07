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

import com.oms.model.Company;
import com.oms.model.Creative;
import com.oms.service.CompanyService;
import com.oms.service.OMSUserService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

/**
 * Tests the company controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CompanyControllerTest {

	@InjectMocks
	CompanyController companyController;
	
	@Mock
	private CompanyService companyService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;
	
	private List<Company> companyLst;
	
	private Company company;
	
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
		
		companyLst = new ArrayList<Company>();
		company = new Company();
		company.setCompanyId(12345L);
		company.setType("Agency");
		company.setName("Comapny1");
		company.setAddress("Noida");
		companyLst.add(company);
    }
	
	/**
	 * Tests listCompanies() method.
	 */
	@Test
	public void testListCompanies(){
		when(companyService.listCompanies()).thenReturn(companyLst);
		List<Company> list = companyController.listCompanies();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests getCompanyByID() method.
	 */
	@Test
	public void testGetCompanyByID(){
		when(companyService.getCompanyById(any(Long.class))).thenReturn(company);
		Company comp = companyController.getCompanyByID(12345L);
		Assert.assertNotNull(comp);
	}
	
	/**
	 * Tests removeCompany() method.
	 */
	@Test
	public void testRemoveCompany(){
		Mockito.doNothing().when(companyService).removeCompany(any(Long.class));
	}
	
	/**
	 * Tests searchCompany() method.
	 */
	@Test
	public void testSearchCompany() {
		SearchRequestVO searchRequest = new SearchRequestVO();
		searchRequest.setLength(10);
		searchRequest.setDraw(10);
		when(companyService.searchCompany(any(SearchRequestVO.class))).thenReturn(new PaginationResponseVO<Company>());
		PaginationResponseVO<Company> cmpny = companyController.searchCompany(searchRequest);
		Assert.assertNotNull(cmpny);
	}
	
	/**
	 * Tests updateCompany() method.
	 */
	@Test
	public void testUpdateCompany() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(companyService.updateCompany(any(Company.class))).thenReturn(company);
		Company comp = companyController.updateCompany(company);
		Assert.assertNotNull(comp);
	}
	
	/**
	 * Tests addCompany() method.
	 */
	@Test
	public void testAddCompany() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(companyService.addCompany(any(Company.class))).thenReturn(company);
		Company comp = companyController.addCompany(company);
		Assert.assertNotNull(comp);
	}

}
