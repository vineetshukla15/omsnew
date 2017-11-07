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
import com.oms.repository.ContactRepository;

/**
 * Tests the contact service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactServiceImplTest {

	@InjectMocks
	ContactServiceImpl contactServiceImpl;
	
	@Mock
	private ContactRepository contactRepository;
	
	private List<Contact> contactLst;
	
	private Contact contact;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		contactLst = new ArrayList<Contact>();
		contact = new Contact();
		contact.setContactId(12345L);
		contact.setContactName("Name1");
		contact.setContactEmail("abcd@gmail.com");
		contact.setContactPhone("1234567890");
		contact.setContactMobile("1234567890");
		contact.setDeleted(false);
		contactLst.add(contact);
    }
	
	/**
	 * Tests positive scenario for addContact() method.
	 */
	@Test
	public void testAddContactPositive() {
		when(contactRepository.save(any(Contact.class))).thenReturn(contact);
		Contact cntct = contactServiceImpl.addContact(contact);
		Assert.assertNotNull(cntct);
	}
	
	/**
	 * Tests negative scenario for updateContact() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateContactNegative() {
		contact.setContactId(null);
		Contact cntct = contactServiceImpl.updateContact(contact);
	}
	
	/**
	 * Tests positive scenario for updateContact() method.
	 */
	@Test
	public void testUpdateContactPositive() {
		when(contactRepository.save(any(Contact.class))).thenReturn(contact);
		Contact cntct = contactServiceImpl.updateContact(contact);
		Assert.assertNotNull(cntct);
	}
	
	/**
	 * Tests positive scenario for listContacts() method.
	 */
	@Test
	public void testListContactsPositive() {
		when(contactRepository.findByDeletedFalseOrderByContactNameAsc()).thenReturn(contactLst);
		List<Contact> list = contactServiceImpl.listContacts();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests positive scenario for getContactById() method.
	 */
	@Test
	public void testGetContactByIdPositive() {
		when(contactRepository.findById(any(Long.class))).thenReturn(contact);
		Contact cntct = contactServiceImpl.getContactById(12345L);
		Assert.assertNotNull(cntct);
	}
	
	/**
	 * Tests negative scenario for getContactById() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetContactByIdNegative() {
		when(contactRepository.findById(any(Long.class))).thenReturn(null);
		Contact cntct = contactServiceImpl.getContactById(12345L);
	}
	
	/**
	 * Tests negative scenario for removeContact() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testRemoveContactNegative() {
		when(contactRepository.findById(any(Long.class))).thenReturn(null);
		contactServiceImpl.removeContact(12345L);
	}
	
	/**
	 * Tests positive scenario for removeContact() method.
	 */
	@Test
	public void testRemoveContactPositive() {
		when(contactRepository.findById(any(Long.class))).thenReturn(contact);
		Mockito.doNothing().when(contactRepository).softDelete(any(Long.class));
		contactServiceImpl.removeContact(12345L);
	}
}
