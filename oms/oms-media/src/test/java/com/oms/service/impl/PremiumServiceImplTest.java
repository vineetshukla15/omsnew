/**
 * 
 */
package com.oms.service.impl;

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

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Premium;
import com.oms.repository.PremiumRepository;

/**
 * Tests the company service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PremiumServiceImplTest {
	
	@InjectMocks
	PremiumServiceImpl premiumServiceImpl;
	
	@Mock
	private PremiumRepository premiumRepository;

	private List<Premium> premiumLst;
	
	private Premium premium;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		premiumLst = new ArrayList<Premium>();
		premium = new Premium();
		premium.setPremiumId(12345L);
		premium.setDeleted(false);
		premiumLst.add(premium);
    }
	
	/**
	 * Tests positive scenario for getAllPremium() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testGetAllPremiumPositive() throws IllegalAccessException, InvocationTargetException {
		when(premiumRepository.findAll()).thenReturn(premiumLst);
		List<Premium> list = premiumServiceImpl.getAllPremium();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests positive scenario for addPremium() method.
	 * @throws Exception 
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testAddPremiumPositive() throws IllegalAccessException, InvocationTargetException, OMSSystemException, Exception {
		when(premiumRepository.save(any(Premium.class))).thenReturn(premium);
		Premium premiums = premiumServiceImpl.addPremium(premium);
		Assert.assertNotNull(premiums);
	}
	
	/**
	 * Tests negative scenario for updatePremium() method.
	 * @throws Exception 
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdatePremiumNegative() throws IllegalAccessException, InvocationTargetException, OMSSystemException, Exception {
		premium.setPremiumId(null);
		Premium premiums = premiumServiceImpl.updatePremium(premium);
	}
	
	/**
	 * Tests positive scenario for updatePremium() method.
	 * @throws Exception 
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testUpdatePremiumPositive() throws IllegalAccessException, InvocationTargetException, OMSSystemException, Exception {
		when(premiumRepository.save(any(Premium.class))).thenReturn(premium);
		Premium premiums = premiumServiceImpl.updatePremium(premium);
		Assert.assertNotNull(premiums);
	}
	
	/**
	 * Tests positive scenario for getPremium() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws OMSSystemException 
	 */
	@Test
	public void testGetPremiumPositive() throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		when(premiumRepository.findById(any(Long.class))).thenReturn(premium);
		Premium premiums = premiumServiceImpl.getPremium(12345L);
		Assert.assertNotNull(premiums);
	}
	
	/**
	 * Tests negative scenario for getPremium() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws OMSSystemException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetPremiumNegative() throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		when(premiumRepository.findById(any(Long.class))).thenReturn(null);
		Premium premiums = premiumServiceImpl.getPremium(12345L);
	}
	
	/**
	 * Tests negative scenario for deletePremium() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testDeletePremiumNegative() throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		when(premiumRepository.findById(any(Long.class))).thenReturn(null);
		premiumServiceImpl.deletePremium(12345L);
	}
	
	/**
	 * Tests positive scenario for deletePremium() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testDeletePremiumPositive() throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		when(premiumRepository.findById(any(Long.class))).thenReturn(premium);
		Mockito.doNothing().when(premiumRepository).softDelete(any(Long.class));
		premiumServiceImpl.deletePremium(12345L);
	}
}
