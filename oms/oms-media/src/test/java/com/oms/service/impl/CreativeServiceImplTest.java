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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Creative;
import com.oms.model.specification.CreativeSpecification;
import com.oms.repository.CreativeRepository;
import com.oms.service.OMSUserService;

/**
 * Tests the creative service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CreativeServiceImplTest {
	
	@InjectMocks
	CreativeServiceImpl creativeServiceImpl;
	
	@Mock
	private CreativeRepository creativeRepository;
	
	private List<Creative> creativeLst;

	private Creative creative;
	
	/**
	 * Sets up the test data.
	 */
	@Before
	public void setUp() {
		creativeLst = new ArrayList<Creative>();
		creative = new Creative();
		creative.setCreativeId(12345L);
		creative.setHeight1(10.0);
		creative.setWidth1(5.0);
		creative.setHeight2(20.0);
		creative.setWidth2(15.0);
		creative.setName("Name1");
		creative.setDescription("Description1");
		creative.setDeleted(false);
		creativeLst.add(creative);
	}

	/**
	 * Tests positive scenario for getCreative() method.
	 */
	@Test
	public void testGetAllCreativesPositive() {
		when(creativeRepository.findByDeletedFalseOrderByNameAsc()).thenReturn(creativeLst);
		List<Creative> list = creativeServiceImpl.getAllCreatives();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests positive scenario for getCreativesByName() method.
	 */
	@Test
	public void testGetCreativesByNamePositive() {
		when(creativeRepository.findCreativeByName(any(String.class), any(Boolean.class))).thenReturn(creativeLst);
		List<Creative> list = creativeServiceImpl.getCreativesByName("Name1");
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests negative scenario for getCreativesByName() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetCreativesByNameNegative() {
		when(creativeRepository.findCreativeByName(any(String.class), any(Boolean.class))).thenReturn(null);
		List<Creative> list = creativeServiceImpl.getCreativesByName("Name1");
	}
	
	/**
	 * Tests positive scenario for getCreativeById() method.
	 */
	@Test
	public void testGetCreativeByIdPositive() {
		when(creativeRepository.findById(any(Long.class))).thenReturn(creative);
		Creative creatve = creativeServiceImpl.getCreativeById(12345L);
		Assert.assertNotNull(creatve);
	}
	
	/**
	 * Tests negative scenario for getCreativeById() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetCreativeByIdNegative() {
		when(creativeRepository.findById(any(Long.class))).thenReturn(null);
		Creative creatve = creativeServiceImpl.getCreativeById(12345L);
	}
	
	/**
	 * Tests negative scenario for addCreative() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testAddCreativeNegative() {
		when(creativeRepository.findCreativeByNameCaseInsensitive(any(String.class), any(Boolean.class))).thenReturn(creative);
		Creative creatve = creativeServiceImpl.addCreative(creative);
	}
	
	/**
	 * Tests positive scenario for addCreative() method.
	 */
	@Test
	public void testAddCreativePositive() {
		when(creativeRepository.findCreativeByNameCaseInsensitive(any(String.class), any(Boolean.class))).thenReturn(null);
		when(creativeRepository.save(any(Creative.class))).thenReturn(creative);
		Creative creatve = creativeServiceImpl.addCreative(creative);
		Assert.assertNotNull(creatve);
	}
	
	/**
	 * Tests negative scenario for updateCreative() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateCreativeNegative() {
		creative.setCreativeId(null);
		Creative creatve = creativeServiceImpl.updateCreative(creative);
	}
	
	/**
	 * Tests positive scenario for updateCreative() method.
	 */
	@Test
	public void testUpdateCreativePositive() {
		when(creativeRepository.save(any(Creative.class))).thenReturn(creative);
		Creative creatve = creativeServiceImpl.updateCreative(creative);
		Assert.assertNotNull(creatve);
	}
	
	/**
	 * Tests negative scenario for deleteCreative() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testDeleteCreativeNegative() {
		when(creativeRepository.findById(any(Long.class))).thenReturn(null);
		creativeServiceImpl.deleteCreative(12345L);
	}
	
	/**
	 * Tests positive scenario for deleteCreative() method.
	 */
	@Test
	public void testDeleteCreativePositive() {
		when(creativeRepository.findById(any(Long.class))).thenReturn(creative);
		Mockito.doNothing().when(creativeRepository).softDelete(any(Long.class));
		creativeServiceImpl.deleteCreative(12345L);
	}
	
	/**
	 * Tests searchCreatives() method.
	 */
	@Test
	public void testSearchCreatives() {
		Page<Creative> pageResponse = Mockito.mock(Page.class);
		when(creativeRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(pageResponse);
		when(creativeRepository.save(any(Creative.class))).thenReturn(creative);
		Creative creatve = creativeServiceImpl.addCreative(creative);
		Assert.assertNotNull(creatve);
	}
	
}
