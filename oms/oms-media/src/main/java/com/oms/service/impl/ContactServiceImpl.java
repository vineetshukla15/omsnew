package com.oms.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.commons.utils.SearchUtil;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.Contact;
import com.oms.model.specification.ContactSpecification;
import com.oms.repository.ContactRepository;
import com.oms.service.ContactService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public class ContactServiceImpl implements ContactService{
	
	final static Logger LOGGER = Logger.getLogger(ContactServiceImpl.class);
	
	@Autowired
	private ContactRepository contactRepository;

	@Transactional
	public Contact addContact(Contact contactDTO)throws OMSSystemException {
		try{
		return contactRepository.save(contactDTO);
		}catch(Exception e){
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Transactional
	public Contact updateContact(Contact contactDTO) throws OMSSystemException {
		if(contactDTO.getContactId()!=null && Boolean.FALSE.equals(contactDTO.isDeleted())){
			contactRepository.save(contactDTO);
			return contactDTO;
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"Contact with id {"+contactDTO.getContactId()+"} does not exist");
	}

	@Transactional
	public List<Contact> listContacts() {
		List<Contact> contactList = null;
		try {
			contactList = contactRepository.findByDeletedFalseOrderByContactNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive Contact information",ex);
		}
		return contactList;
	}

	@Transactional
	public Contact getContactById(Long contactID) throws OMSSystemException {
		if(contactRepository.findById(contactID)!=null){
			return contactRepository.findById(contactID);
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"Contact with id {"+contactID+"} does not exist");
	}

	@Transactional
	public void removeContact(Long contactId) throws OMSSystemException {
		Contact contact = contactRepository.findById(contactId);
		if(contact != null && Boolean.FALSE.equals(contact.isDeleted())){
			contactRepository.softDelete(contactId);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Contact with id {"+contactId+"} does not exist or already deleted");
		}
	}

	@Override
	public PaginationResponseVO<Contact> searchContact(SearchRequestVO searchRequest) {
		Page<Contact> pageResponse = null;
		PaginationResponseVO<Contact> response = null;
		try {
			pageResponse = contactRepository.findAll(ContactSpecification.getContactSpecification(searchRequest),
					SearchUtil.getPageable(searchRequest));
			response = new PaginationResponseVO<Contact>(pageResponse.getTotalElements(), searchRequest.getDraw(),
					pageResponse.getTotalElements(), pageResponse.getContent());
		} catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND, "No Data Found");
		}

		System.out.println("");
		return response;
	}

}
