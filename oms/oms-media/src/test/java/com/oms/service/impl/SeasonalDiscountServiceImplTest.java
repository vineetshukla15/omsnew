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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.tavant.api.auth.model.OMSUser;

import com.oms.controller.SeasonalDiscountController;
import com.oms.exceptions.OMSSystemException;
import com.oms.model.Contact;
import com.oms.model.SeasonalDiscount;
import com.oms.repository.SeasonalDiscountRepository;
import com.oms.service.SeasonalDiscountService;

/**
 * Tests the Seasonal Discount service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SeasonalDiscountServiceImplTest {
	
	@InjectMocks
	SeasonalDiscountServiceImpl seasonalDiscountServiceImpl;
	
	@Mock
	private SeasonalDiscountRepository seasonalDiscountRepository;
	
	private List<SeasonalDiscount> seasonalDiscountLst;
	
	private SeasonalDiscount seasonalDiscount;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		seasonalDiscountLst = new ArrayList<SeasonalDiscount>();
		seasonalDiscount = new SeasonalDiscount();
		seasonalDiscount.setId(12345L);
		seasonalDiscount.setDeleted(false);
		seasonalDiscount.setRuleName("Rule1");
		seasonalDiscountLst.add(seasonalDiscount);
    }
	
	/**
	 * Tests positive scenario for getAllSeasonalDiscount() method.
	 */
	@Test
	public void testGetAllSeasonalDiscountPositive() {
		when(seasonalDiscountRepository.findByDeletedFalse()).thenReturn(seasonalDiscountLst);
		List<SeasonalDiscount> list = seasonalDiscountServiceImpl.getAllSeasonalDiscount();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests positive scenario for addSeasonalDiscount() method.
	 * @throws Exception 
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testAddSeasonalDiscountPositive() throws IllegalAccessException, InvocationTargetException, OMSSystemException, Exception {
		when(seasonalDiscountRepository.save(any(SeasonalDiscount.class))).thenReturn(seasonalDiscount);
		SeasonalDiscount discount = seasonalDiscountServiceImpl.addSeasonalDiscount(seasonalDiscount);
		Assert.assertNotNull(discount);
	}
	
	/**
	 * Tests negative scenario for updateSeasonalDiscount() method.
	 * @throws Exception 
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateSeasonalDiscountNegative() throws IllegalAccessException, InvocationTargetException, OMSSystemException, Exception {
		seasonalDiscount.setId(null);
		SeasonalDiscount discount = seasonalDiscountServiceImpl.updateSeasonalDiscount(seasonalDiscount);
	}
	
	/**
	 * Tests positive scenario for updateSeasonalDiscount() method.
	 * @throws Exception 
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testUpdateSeasonalDiscountPositive() throws IllegalAccessException, InvocationTargetException, OMSSystemException, Exception {
		when(seasonalDiscountRepository.save(any(SeasonalDiscount.class))).thenReturn(seasonalDiscount);
		SeasonalDiscount discount = seasonalDiscountServiceImpl.updateSeasonalDiscount(seasonalDiscount);
		Assert.assertNotNull(discount);
	}
	
	/**
	 * Tests positive scenario for getSeasonalDiscount() method.
	 */
	@Test
	public void testGetSeasonalDiscountPositive() {
		when(seasonalDiscountRepository.findById(any(Long.class))).thenReturn(seasonalDiscount);
		SeasonalDiscount discount = seasonalDiscountServiceImpl.getSeasonalDiscount(12345L);
		Assert.assertNotNull(discount);
	}
	
	/**
	 * Tests negative scenario for getSeasonalDiscount() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetSeasonalDiscountNegative() {
		when(seasonalDiscountRepository.findById(any(Long.class))).thenReturn(null);
		SeasonalDiscount discount = seasonalDiscountServiceImpl.getSeasonalDiscount(12345L);
	}
	
	/**
	 * Tests negative scenario for deleteSeasonalDiscount() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testDeleteSeasonalDiscountNegative() {
		when(seasonalDiscountRepository.findById(any(Long.class))).thenReturn(null);
		seasonalDiscountServiceImpl.deleteSeasonalDiscount(12345L);
	}
	
	/**
	 * Tests positive scenario for deleteSeasonalDiscount() method.
	 */
	@Test
	public void testDeleteSeasonalDiscountPositive() {
		when(seasonalDiscountRepository.findById(any(Long.class))).thenReturn(seasonalDiscount);
		Mockito.doNothing().when(seasonalDiscountRepository).softDelete(any(Long.class));
		seasonalDiscountServiceImpl.deleteSeasonalDiscount(12345L);
	}

}
