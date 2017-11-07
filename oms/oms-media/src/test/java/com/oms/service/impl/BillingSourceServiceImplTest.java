/**
 * 
 */
package com.oms.service.impl;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.oms.model.BillingSource;
import com.oms.repository.BillingSourceRepository;

/**
 * Tests the Billing Source service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BillingSourceServiceImplTest {
	
	@InjectMocks
	BillingSourceServiceImpl billingSourceServiceImpl;
	
	@Mock
	private BillingSourceRepository billingSourceRepository;
	
    private List<BillingSource> billingList;
	
	private BillingSource billingSource;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {		
		billingList = new ArrayList<BillingSource>();
		billingSource = new BillingSource();
		billingSource.setBillingSourceId(12345L);
		billingSource.setDeleted(false);
		billingList.add(billingSource);
    }
	
	/**
	 * Tests positive scenario for getAllBillingSource() method.
	 */
	@Test
	public void testGetAllBillingSourcePositive() {
		when(billingSourceRepository.findByDeletedFalseOrderByNameAsc()).thenReturn(billingList);
		List<BillingSource> list = billingSourceServiceImpl.getAllBillingSource();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
}
