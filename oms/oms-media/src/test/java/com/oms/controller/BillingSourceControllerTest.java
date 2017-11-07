/**
 * 
 */
package com.oms.controller;

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
import com.oms.model.Contact;
import com.oms.service.BillingSourceService;

/**
 * Tests the Contact controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BillingSourceControllerTest {
	
	@InjectMocks
	BillingSourceController billingSourceController;
	
	@Mock
	private BillingSourceService billingSourceService;
	
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
		billingList.add(billingSource);
    }
	
	/**
	 * Tests getBillingSource() method.
	 */
	@Test
	public void testGetBillingSource(){
		when(billingSourceService.getAllBillingSource()).thenReturn(billingList);
		List<BillingSource> list = billingSourceController.getBillingSource();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

}
