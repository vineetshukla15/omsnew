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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Company;
import com.oms.model.CompanyStatus;
import com.oms.repository.CompanyStatusRepository;

/**
 * Tests the Company Status service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CompanyStatusServiceImplTest {
	
	@InjectMocks
	CompanyStatusServiceImpl companyStatusServiceImpl;
	
	@Mock
	private CompanyStatusRepository companyStatusRepository;

	private List<CompanyStatus> companyStatusList;
	
	private CompanyStatus companyStatus;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		companyStatusList = new ArrayList<CompanyStatus>();
		companyStatus = new CompanyStatus();
		companyStatus.setDeleted(false);
		companyStatus.setCompanyStatusId(12345L);
		companyStatus.setName("ComStatus");                
		companyStatus.setStatus(true);
		companyStatusList.add(companyStatus);
    }
	
	/**
	 * Tests addCompanyStatus() method.
	 */
	@Test
	public void testAddCompanyStatus() {
		when(companyStatusRepository.save(any(CompanyStatus.class))).thenReturn(companyStatus);
		CompanyStatus status = companyStatusServiceImpl.addCompanyStatus(companyStatus);
		Assert.assertNotNull(status);
	}
	
	/**
	 * Tests negative scenario for updateCompanyStatus() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateCompanyStatusNegative() {
		companyStatus.setCompanyStatusId(null);
		CompanyStatus status = companyStatusServiceImpl.updateCompanyStatus(companyStatus);
	}
	
	/**
	 * Tests positive scenario for updateCompanyStatus() method.
	 */
	@Test
	public void testUpdateCompanyStatusPositive() {
		when(companyStatusRepository.save(any(CompanyStatus.class))).thenReturn(companyStatus);
		CompanyStatus status = companyStatusServiceImpl.updateCompanyStatus(companyStatus);
		Assert.assertNotNull(status);
	}
	
	/**
	 * Tests positive scenario for listCompaniesStatus() method.
	 */
	@Test
	public void testListCompaniesStatusPositive() {
		when(companyStatusRepository.findByDeletedFalseOrderByNameAsc()).thenReturn(companyStatusList);
		List<CompanyStatus> list = companyStatusServiceImpl.listCompaniesStatus();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests positive scenario for getCompanyStatusById() method.
	 */
	@Test
	public void testGetCompanyStatusByIdPositive() {
		when(companyStatusRepository.findById(any(Long.class))).thenReturn(companyStatus);
		CompanyStatus status = companyStatusServiceImpl.getCompanyStatusById(12345L);
		Assert.assertNotNull(status);
	}
	
	/**
	 * Tests negative scenario for getCompanyStatusById() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetCompanyStatusByIdNegative() {
		when(companyStatusRepository.findById(any(Long.class))).thenReturn(null);
		CompanyStatus status = companyStatusServiceImpl.getCompanyStatusById(12345L);
	}
	
	/**
	 * Tests negative scenario for removeCompanyStatus() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testRemoveCompanyStatusNegative() {
		when(companyStatusRepository.findById(any(Long.class))).thenReturn(null);
		companyStatusServiceImpl.removeCompanyStatus(12345L);
	}
	
	/**
	 * Tests positive scenario for removeCompanyStatus() method.
	 */
	@Test
	public void testRemoveCompanyStatusPositive() {
		when(companyStatusRepository.findById(any(Long.class))).thenReturn(companyStatus);
		Mockito.doNothing().when(companyStatusRepository).softDelete(any(Long.class));
		companyStatusServiceImpl.removeCompanyStatus(12345L);
	}
}
