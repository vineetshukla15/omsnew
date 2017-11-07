package com.oms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Contact;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public interface ContactService {
	
	public Contact addContact(Contact contactDTO);
	public Contact updateContact(Contact contactDTO) throws OMSSystemException;
	public List<Contact> listContacts();
	public Contact getContactById(Long contactID) throws OMSSystemException;
	public void removeContact(Long contactId)throws OMSSystemException;
	public PaginationResponseVO<Contact> searchContact(SearchRequestVO searchRequest);

}
