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
import com.oms.model.ADUnit;
import com.oms.repository.ADUnitRepository;

/**
 * Tests the ADUnit service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ADUnitServiceImplTest {
	
	@InjectMocks
	ADUnitServiceImpl aDUnitServiceImpl;
	
	@Mock
	private ADUnitRepository adUnitRepository;
	
	private List<ADUnit> aDUnitLst;
	
	private ADUnit aDUnit;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {		
		aDUnitLst = new ArrayList<ADUnit>();
		aDUnit = new ADUnit();
		aDUnit.setAdUnitId(12345L);
		aDUnit.setName("Unit1");
		aDUnit.setDeleted(false);
		aDUnitLst.add(aDUnit);
    }

	/**
	 * Tests positive scenario for getAllADUnit() method.
	 */
	@Test
	public void testGetAllADUnitPositive() {
		when(adUnitRepository.findByDeletedFalseOrderByNameAsc()).thenReturn(aDUnitLst);
		List<ADUnit> list = aDUnitServiceImpl.getAllADUnit();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests positive scenario for getADUnit() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws OMSSystemException 
	 */
	@Test
	public void testGetADUnitPositive() throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		when(adUnitRepository.findById(any(Long.class))).thenReturn(aDUnit);
		ADUnit unit = aDUnitServiceImpl.getADUnit(12345L);
		Assert.assertNotNull(unit);
	}
	
	/**
	 * Tests negative scenario for getADUnit() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws OMSSystemException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetADUnitNegative() throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		when(adUnitRepository.findById(any(Long.class))).thenReturn(null);
		ADUnit unit = aDUnitServiceImpl.getADUnit(12345L);
	}
	
	/**
	 * Tests positive scenario for addADUnit() method.
	 * @throws Exception 
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testAddADUnitPositive() throws IllegalAccessException, InvocationTargetException, OMSSystemException, Exception {
		when(adUnitRepository.save(any(ADUnit.class))).thenReturn(aDUnit);
		ADUnit unit = aDUnitServiceImpl.addADUnit(aDUnit);
		Assert.assertNotNull(unit);
	}
	
	/**
	 * Tests negative scenario for updateADUnit() method.
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateADUnitNegative() throws IllegalAccessException, InvocationTargetException, Exception {
		aDUnit.setAdUnitId(null);
		ADUnit unit = aDUnitServiceImpl.updateADUnit(aDUnit);
	}
	
	/**
	 * Tests positive scenario for updateADUnit() method.
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testUpdateADUnitPositive() throws IllegalAccessException, InvocationTargetException, Exception {
		when(adUnitRepository.save(any(ADUnit.class))).thenReturn(aDUnit);
		ADUnit unit = aDUnitServiceImpl.updateADUnit(aDUnit);
		Assert.assertNotNull(unit);
	}	
	
	/**
	 * Tests negative scenario for deleteADUnit() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testDeleteADUnitNegative() throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		aDUnit.setDeleted(true);
		when(adUnitRepository.findById(any(Long.class))).thenReturn(aDUnit);
		aDUnitServiceImpl.deleteADUnit(12345L);
	}
	
	/**
	 * Tests positive scenario for deleteADUnit() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testDeleteADUnitPositive() throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		when(adUnitRepository.findById(any(Long.class))).thenReturn(aDUnit);
		Mockito.doNothing().when(adUnitRepository).softDelete(any(Long.class));
		aDUnitServiceImpl.deleteADUnit(12345L);
	}
}
