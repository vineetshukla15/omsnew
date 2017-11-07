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

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Company;
import com.oms.repository.CompanyRepository;

/**
 * Tests the company service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceImplTest {
	
	@InjectMocks
	CompanyServiceImpl companyServiceImpl;
	
	@Mock
	private CompanyRepository companyRepository;
	
    private List<Company> companyLst;
	
	private Company company;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		companyLst = new ArrayList<Company>();
		company = new Company();
		company.setCompanyId(12345L);
		company.setType("Agency");
		company.setName("Comapny1");
		company.setAddress("Noida");
		company.setDeleted(false);
		companyLst.add(company);
    }
	
	/**
	 * Tests positive scenario for addCompany() method.
	 */
	@Test
	public void testAddCompanyPositive() {
		when(companyRepository.save(any(Company.class))).thenReturn(company);
		Company cmpny = companyServiceImpl.addCompany(company);
		Assert.assertNotNull(cmpny);
	}
	
	/**
	 * Tests positive scenario for listCompanies() method.
	 */
	@Test
	public void testListCompaniesPositive() {
		when(companyRepository.findByDeletedFalseOrderByNameAsc()).thenReturn(companyLst);
		List<Company> list = companyServiceImpl.listCompanies();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * Tests negative scenario for updateCompany() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateCompanyNegative() {
		company.setCompanyId(null);
		Company cmpny = companyServiceImpl.updateCompany(company);
	}
	
	/**
	 * Tests positive scenario for updateCompany() method.
	 */
	@Test
	public void testUpdateCompanyPositive() {
		when(companyRepository.save(any(Company.class))).thenReturn(company);
		Company cmpny = companyServiceImpl.updateCompany(company);
		Assert.assertNotNull(cmpny);
	}
	
	/**
	 * Tests positive scenario for getCompanyById() method.
	 */
	@Test
	public void testGetCompanyByIdPositive() {
		when(companyRepository.findById(any(Long.class))).thenReturn(company);
		Company cmpny = companyServiceImpl.getCompanyById(12345L);
		Assert.assertNotNull(cmpny);
	}
	
	/**
	 * Tests negative scenario for getCompanyById() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetCompanyByIdNegative() {
		when(companyRepository.findById(any(Long.class))).thenReturn(null);
		Company cmpny = companyServiceImpl.getCompanyById(12345L);
	}
	
	/**
	 * Tests negative scenario for removeCompany() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testRemoveCompanyNegative() {
		when(companyRepository.findById(any(Long.class))).thenReturn(null);
		companyServiceImpl.removeCompany(12345L);
	}
	
	/**
	 * Tests positive scenario for removeCompany() method.
	 */
	@Test
	public void testRemoveCompanyPositive() {
		when(companyRepository.findById(any(Long.class))).thenReturn(company);
		Mockito.doNothing().when(companyRepository).softDelete(any(Long.class));
		companyServiceImpl.removeCompany(12345L);
	}
}
