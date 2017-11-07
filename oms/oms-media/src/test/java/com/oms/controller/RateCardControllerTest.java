/**
 * 
 */
package com.oms.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.tavant.api.auth.model.OMSUser;

import com.oms.commons.utils.Convertor;
import com.oms.model.Contact;
import com.oms.model.RateCard;
import com.oms.service.OMSUserService;
import com.oms.service.RateCardService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.RateCardVO;
import com.oms.viewobjects.SearchRequestVO;

/**
 * Tests the Rate Card controller.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Convertor.class})
public class RateCardControllerTest {

	@InjectMocks
	private RateCardController rateCardController;
	
	@Mock
	private RateCardService rateCardService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;
	
	private List<RateCard> rateCardLst;
	
	private RateCard rateCard;
	
    OMSUser user;
	
	Authentication authentication;
	
	SecurityContext securityContext;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		PowerMockito.mockStatic(Convertor.class);
		
		authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.getPrincipal()).thenReturn("abc");
		securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		user = new OMSUser();
		user.setUsername("abc");
		user.setId(12345L);
		
		rateCardLst = new ArrayList<RateCard>();
		rateCard = new RateCard();
		rateCard.setRateCardId(12345L);
		rateCard.setDeleted(false);
		rateCard.setSectionsName("Section1");
		rateCardLst.add(rateCard);
    }
	
	/**
	 * Tests getAllRateCard() method.
	 */
	@Test
	public void testGetAllRateCard(){
		when(rateCardService.getAllRateCard()).thenReturn(rateCardLst);
		when(Convertor.toRateCardVO(Mockito.any(RateCard.class)))
		  .thenReturn(new RateCardVO());
		when(Convertor.convert(Mockito.any(Collection.class), Mockito.any(Function.class)))
		  .thenReturn(new ArrayList<RateCardVO>());
		Collection<RateCardVO> list = rateCardController.getAllRateCard();
		Assert.assertNotNull(list);
	}
	
	/**
	 * Tests deleteRateCard() method.
	 */
	@Test
	public void testDeleteRateCard(){
		Mockito.doNothing().when(rateCardService).deleteRateCard(any(Long.class));
	}
	
	/**
	 * Tests updateRateCard() method.
	 */
	@Test
	public void testUpdateRateCard() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(rateCardService.updateRateCard(any(RateCard.class))).thenReturn(rateCard);
		RateCard card = rateCardController.updateRateCard(rateCard);
		Assert.assertNotNull(card);
	}
	
	/**
	 * Tests addNewRateCard() method.
	 */
	@Test
	public void testAddNewRateCard() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(rateCardService.addRateCard(any(RateCard.class))).thenReturn(rateCard);
		RateCard card = rateCardController.addNewRateCard(rateCard);
		Assert.assertNotNull(card);
	}
	
	/**
	 * Tests getRateCard() method.
	 */
	@Test
	public void testGetRateCard(){
		when(rateCardService.getRateCard(any(Long.class))).thenReturn(rateCard);
		when(Convertor.toRateCardVO(Mockito.any(RateCard.class)))
		  .thenReturn(new RateCardVO());
		RateCardVO card = rateCardController.getRateCard(12345L);
		Assert.assertNotNull(card);
	}
	
	/**
	 * Tests searchRateCard() method.
	 */
	@Test
	public void testSearchRateCard() {
		SearchRequestVO searchRequest = new SearchRequestVO();
		searchRequest.setLength(10);
		searchRequest.setDraw(10);
		when(rateCardService.searchRateCard(any(SearchRequestVO.class))).thenReturn(new PaginationResponseVO<RateCard>());
		when(Convertor.toRateCardVO(Mockito.any(RateCard.class)))
		  .thenReturn(new RateCardVO());
		when(Convertor.toPaginationVO(Mockito.any(PaginationResponseVO.class), Mockito.any(Function.class)))
		  .thenReturn(new PaginationResponseVO<RateCardVO>());
		PaginationResponseVO<RateCardVO> card = rateCardController.searchProposal(searchRequest);
		Assert.assertNotNull(card);
	}
}
