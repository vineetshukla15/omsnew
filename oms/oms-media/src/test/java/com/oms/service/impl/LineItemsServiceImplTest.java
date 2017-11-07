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
import com.oms.model.LineItems;
import com.oms.repository.LineItemsRepository;

/**
 * Tests the Line Items service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LineItemsServiceImplTest {

	@InjectMocks
	LineItemsServiceImpl lineItemsServiceImpl;
	
	@Mock
	private LineItemsRepository lineItemsRepository;
	
	private List<LineItems> lineItemsLst;
	
	private LineItems lineItems;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		lineItemsLst = new ArrayList<LineItems>();
		lineItems = new LineItems();
		lineItems.setLineItemId(12345L);
		lineItems.setDeleted(false);
		lineItems.setName("Item1");
		lineItemsLst.add(lineItems);
    }
	
	/**
	 * Tests positive scenario for addLineItem() method.
	 */
	@Test
	public void testAddLineItemPositive() {
		when(lineItemsRepository.save(any(LineItems.class))).thenReturn(lineItems);
		LineItems items = lineItemsServiceImpl.addLineItem(lineItems);
		Assert.assertNotNull(items);
	}
	
	/**
	 * Tests negative scenario for updateLineItems() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateLineItemsNegative() {
		lineItems.setLineItemId(null);
		LineItems items = lineItemsServiceImpl.updateLineItems(lineItems);
	}
	
	/**
	 * Tests positive scenario for updateLineItems() method.
	 */
	@Test
	public void testUpdateLineItemsPositive() {
		when(lineItemsRepository.save(any(LineItems.class))).thenReturn(lineItems);
		LineItems items = lineItemsServiceImpl.updateLineItems(lineItems);
		Assert.assertNotNull(items);
	}
	
	/**
	 * Tests positive scenario for getAllLineItems() method.
	 */
	@Test
	public void testGetAllLineItemsPositive() {
		when(lineItemsRepository.findAll()).thenReturn(lineItemsLst);
		List<LineItems> list = lineItemsServiceImpl.getAllLineItems();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests positive scenario for getLineItemById() method.
	 */
	@Test
	public void testGetLineItemByIdPositive() {
		when(lineItemsRepository.findById(any(Long.class))).thenReturn(lineItems);
		LineItems items = lineItemsServiceImpl.getLineItemById(12345L);
		Assert.assertNotNull(items);
	}
	
	/**
	 * Tests negative scenario for getLineItemById() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetLineItemByIdNegative() {
		when(lineItemsRepository.findById(any(Long.class))).thenReturn(null);
		LineItems items = lineItemsServiceImpl.getLineItemById(12345L);
	}
	
	/**
	 * Tests negative scenario for removeLineItems() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testRemoveLineItemsNegative() {
		when(lineItemsRepository.findById(any(Long.class))).thenReturn(null);
		lineItemsServiceImpl.removeLineItems(12345L);
	}
	
	/**
	 * Tests positive scenario for removeLineItems() method.
	 */
	@Test
	public void testRemoveLineItemsPositive() {
		when(lineItemsRepository.findById(any(Long.class))).thenReturn(lineItems);
		Mockito.doNothing().when(lineItemsRepository).softDelete(any(Long.class));
		lineItemsServiceImpl.removeLineItems(12345L);
	}
}
