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
import com.oms.model.Contact;
import com.oms.model.RateCard;
import com.oms.repository.RateCardRepository;

/**
 * Tests the Rate Card service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RateCardServiceImplTest {

	@InjectMocks
	RateCardServiceImpl rateCardServiceImpl;
	
	@Mock
	private RateCardRepository rateCardRepository;
	
	private List<RateCard> rateCardLst;
	
	private RateCard rateCard;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		rateCardLst = new ArrayList<RateCard>();
		rateCard = new RateCard();
		rateCard.setRateCardId(12345L);
		rateCard.setDeleted(false);
		rateCard.setSectionsName("Section1");
		rateCardLst.add(rateCard);
    }
	
	/**
	 * Tests positive scenario for getAllRateCard() method.
	 */
	@Test
	public void testGetAllRateCardPositive() {
		when(rateCardRepository.findByDeletedFalseOrderBySectionsNameAsc()).thenReturn(rateCardLst);
		List<RateCard> list = rateCardServiceImpl.getAllRateCard();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests positive scenario for addRateCard() method.
	 */
	@Test
	public void testAddRateCardPositive() {
		when(rateCardRepository.save(any(RateCard.class))).thenReturn(rateCard);
		RateCard card = rateCardServiceImpl.addRateCard(rateCard);
		Assert.assertNotNull(card);
	}
	
	/**
	 * Tests negative scenario for updateRateCard() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateRateCardNegative() {
		rateCard.setRateCardId(null);
		RateCard card = rateCardServiceImpl.updateRateCard(rateCard);
	}
	
	/**
	 * Tests positive scenario for updateRateCard() method.
	 */
	@Test
	public void testUpdateRateCardPositive() {
		when(rateCardRepository.save(any(RateCard.class))).thenReturn(rateCard);
		RateCard card = rateCardServiceImpl.updateRateCard(rateCard);
		Assert.assertNotNull(card);
	}
	
	/**
	 * Tests positive scenario for getRateCard() method.
	 */
	@Test
	public void testGetRateCardPositive() {
		when(rateCardRepository.findById(any(Long.class))).thenReturn(rateCard);
		RateCard card = rateCardServiceImpl.getRateCard(12345L);
		Assert.assertNotNull(card);
	}
	
	/**
	 * Tests negative scenario for getRateCard() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetRateCardNegative() {
		when(rateCardRepository.findById(any(Long.class))).thenReturn(null);
		RateCard card = rateCardServiceImpl.getRateCard(12345L);
	}
	
	/**
	 * Tests negative scenario for deleteRateCard() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testDeleteRateCardNegative() {
		when(rateCardRepository.findById(any(Long.class))).thenReturn(null);
		rateCardServiceImpl.deleteRateCard(12345L);
	}
	
	/**
	 * Tests positive scenario for deleteRateCard() method.
	 */
	@Test
	public void testDeleteRateCardPositive() {
		when(rateCardRepository.findById(any(Long.class))).thenReturn(rateCard);
		Mockito.doNothing().when(rateCardRepository).softDelete(any(Long.class));
		rateCardServiceImpl.deleteRateCard(12345L);
	}
}
