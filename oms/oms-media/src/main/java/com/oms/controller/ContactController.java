package com.oms.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.exceptions.OMSSystemException;
import com.oms.model.Contact;
import com.oms.service.ContactService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@RestController
@RequestMapping(value="/contact",produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactController extends BaseController {

	final static Logger logger = Logger.getLogger(ContactController.class);

	@Autowired
	private ContactService contactService;

	// to get all contacts
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<Contact> listContacts() {
		List<Contact> contacts = contactService.listContacts();
		logger.info("List of Contacts " + contacts.toString());
		return contacts;

	}

	// to add contact
	@RequestMapping(method = RequestMethod.POST)
	@Auditable(actionType = AuditingActionType.ADD_NEW_CONTACT)
	public Contact addContact(@RequestBody Contact contactDTO)throws OMSSystemException {
		contactDTO.setCreatedBy(findUserIDFromSecurityContext());
		contactDTO.setUpdatedBy(findUserIDFromSecurityContext());
		return contactService.addContact(contactDTO);
	}

	// to update contact
	@RequestMapping( method = RequestMethod.PUT)
	@Auditable(actionType = AuditingActionType.UPDATE_CONTACT)
	public Contact updateContact(@RequestBody Contact contactDTO) {
		contactDTO.setUpdatedBy(findUserIDFromSecurityContext());
		return contactService.updateContact(contactDTO);

	}

	// to delete contact
	@RequestMapping(value = "/{contactId}", method = RequestMethod.DELETE)
	@Auditable(actionType = AuditingActionType.DELETE_CONTACT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeContact(@PathVariable("contactId") Long contactId) throws OMSSystemException {
		contactService.removeContact(contactId);

	}

	// to get contact by id

	@RequestMapping(value = "/{contactID}", method = RequestMethod.GET)
	public Contact getContact(@PathVariable long contactID) throws OMSSystemException {
		return contactService.getContactById(contactID);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public PaginationResponseVO<Contact> searchContact(@RequestBody SearchRequestVO searchRequest ){
		return contactService.searchContact(searchRequest);
	}
}
