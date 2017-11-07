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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import com.oms.exceptions.OMSSystemException;
import com.oms.model.AudienceTargetType;
import com.oms.repository.AudienceTargetTypeRepository;

/**
 * Tests the Audience Target Type service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AudienceTargetTypeServiceImplTest {
	
	@InjectMocks
	AudienceTargetTypeServiceImpl audienceTargetTypeServiceImpl;
	
	@Mock
	private AudienceTargetTypeRepository audienceTargetTypeRepository;
	
	private List<AudienceTargetType> targetTypeList;

	private AudienceTargetType targetType;
	
	/**
	 * Sets up the test data.
	 */
	@Before
	public void setUp() {
		targetTypeList = new ArrayList<AudienceTargetType>();
		targetType = new AudienceTargetType();
		targetType.setTargetTypeId(12345L);
		targetType.setName("Target1");
		targetType.setDeleted(false);
		targetTypeList.add(targetType);
	}

	/**
	 * Tests positive scenario for getAllAudienceTargetType() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testGetAllAudienceTargetTypePositive() throws IllegalAccessException, InvocationTargetException {
		when(audienceTargetTypeRepository.findByDeletedFalseOrderByNameAsc()).thenReturn(targetTypeList);
		List<AudienceTargetType> list = audienceTargetTypeServiceImpl.getAllAudienceTargetType();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests addAudienceTargetType() method.
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testAddRolePositive() throws IllegalAccessException, InvocationTargetException, Exception {
		when(audienceTargetTypeRepository.save(any(AudienceTargetType.class))).thenReturn(targetType);
		AudienceTargetType type = audienceTargetTypeServiceImpl.addAudienceTargetType(targetType);
		Assert.assertNotNull(type);
	}
	
	/**
	 * Tests negative scenario for updateAudienceTargetType() method.
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateAudienceTargetTypeNegative() throws IllegalAccessException, InvocationTargetException, Exception {
		targetType.setTargetTypeId(null);
		AudienceTargetType type = audienceTargetTypeServiceImpl.updateAudienceTargetType(targetType);
	}
	
	/**
	 * Tests positive scenario for updateAudienceTargetType() method.
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testUpdateAudienceTargetTypePositive() throws IllegalAccessException, InvocationTargetException, Exception {
		when(audienceTargetTypeRepository.save(any(AudienceTargetType.class))).thenReturn(targetType);
		AudienceTargetType type = audienceTargetTypeServiceImpl.updateAudienceTargetType(targetType);
		Assert.assertNotNull(type);
	}
	
	/**
	 * Tests positive scenario for getAudienceTargetType() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws OMSSystemException 
	 */
	@Test
	public void testGetAudienceTargetTypePositive() throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		when(audienceTargetTypeRepository.findById(any(Long.class))).thenReturn(targetType);
		AudienceTargetType type = audienceTargetTypeServiceImpl.getAudienceTargetType(12345L);
		Assert.assertNotNull(type);
	}
	
	/**
	 * Tests negative scenario for getAudienceTargetType() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws OMSSystemException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetAudienceTargetTypeNegative() throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		when(audienceTargetTypeRepository.findById(any(Long.class))).thenReturn(null);
		AudienceTargetType type = audienceTargetTypeServiceImpl.getAudienceTargetType(12345L);
	}
	
	/**
	 * Tests negative scenario for deleteAudienceTargetType() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testDeleteAudienceTargetTypeNegative() throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		targetType.setDeleted(true);
		when(audienceTargetTypeRepository.findById(any(Long.class))).thenReturn(targetType);
		audienceTargetTypeServiceImpl.deleteAudienceTargetType(12345L);
	}
	
	/**
	 * Tests positive scenario for deleteAudienceTargetType() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testDeleteAudienceTargetTypePositive() throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		when(audienceTargetTypeRepository.findById(any(Long.class))).thenReturn(targetType);
		Mockito.doNothing().when(audienceTargetTypeRepository).softDelete(any(Long.class));
		audienceTargetTypeServiceImpl.deleteAudienceTargetType(12345L);
	}
}
