/**
 * 
 */
package com.oms.controller;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.tavant.api.auth.model.OMSUser;

import com.oms.model.Contact;
import com.oms.service.ContactService;
import com.oms.service.OMSUserService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

/**
 * Tests the Contact controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactControllerTest {

	@InjectMocks
	ContactController contactController;
	
	@Mock
	private ContactService contactService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;
	
	private List<Contact> contactLst;
	
	private Contact contact;
	
    OMSUser user;
	
	Authentication authentication;
	
	SecurityContext securityContext;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.getPrincipal()).thenReturn("abc");
		securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		user = new OMSUser();
		user.setUsername("abc");
		user.setId(12345L);
		
		contactLst = new ArrayList<Contact>();
		contact = new Contact();
		contact.setContactId(12345L);
		contact.setContactName("Name1");
		contact.setContactEmail("abcd@gmail.com");
		contact.setContactPhone("1234567890");
		contact.setContactMobile("1234567890");
		contactLst.add(contact);
    }
	
	/**
	 * Tests listContacts() method.
	 */
	@Test
	public void testListContacts(){
		when(contactService.listContacts()).thenReturn(contactLst);
		List<Contact> list = contactController.listContacts();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests getContact() method.
	 */
	@Test
	public void testGetContact(){
		when(contactService.getContactById(any(Long.class))).thenReturn(contact);
		Contact contct = contactController.getContact(12345L);
		Assert.assertNotNull(contct);
	}
	
	/**
	 * Tests removeContact() method.
	 */
	@Test
	public void testRemoveContact(){
		Mockito.doNothing().when(contactService).removeContact(any(Long.class));
	}
	
	/**
	 * Tests searchContact() method.
	 */
	@Test
	public void testSearchContact() {
		SearchRequestVO searchRequest = new SearchRequestVO();
		searchRequest.setLength(10);
		searchRequest.setDraw(10);
		when(contactService.searchContact(any(SearchRequestVO.class))).thenReturn(new PaginationResponseVO<Contact>());
		PaginationResponseVO<Contact> cntct = contactController.searchContact(searchRequest);
		Assert.assertNotNull(cntct);
	}
	
	/**
	 * Tests updateContact() method.
	 */
	@Test
	public void testUpdateContact() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(contactService.updateContact(any(Contact.class))).thenReturn(contact);
		Contact contct = contactController.updateContact(contact);
		Assert.assertNotNull(contct);
	}
	
	/**
	 * Tests addContact() method.
	 */
	@Test
	public void testAddContact() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(contactService.addContact(any(Contact.class))).thenReturn(contact);
		Contact contct = contactController.addContact(contact);
		Assert.assertNotNull(contct);
	}
}
