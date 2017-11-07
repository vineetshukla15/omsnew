package com.oms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tavant.oms.logging.annotation.Loggable;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.Creative;
import com.oms.model.specification.CreativeSpecification;
import com.oms.repository.CreativeRepository;
import com.oms.service.CreativeService;
import com.oms.viewobjects.Columns;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchOrderVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public class CreativeServiceImpl implements CreativeService {
	final static Logger logger = Logger.getLogger(CreativeServiceImpl.class);
	
	@Autowired
	private CreativeRepository creativeRepository;
	
@Loggable	
	@Transactional
	public List<Creative> getAllCreatives() {
		List<Creative> creativeList = null;
		try {
			creativeList = creativeRepository.findByDeletedFalseOrderByNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive Creatives information",ex);
		}
		return creativeList;
	}

	@Transactional
	@Override
	public PaginationResponseVO<Creative> searchCreatives(SearchRequestVO searchRequest) {
			Page<Creative> pageResponse = null;
			PaginationResponseVO<Creative> response = null; 
			try {
				pageResponse = creativeRepository.findAll(CreativeSpecification.getCreativesSpecification(searchRequest),getPagination(searchRequest));
				response= new PaginationResponseVO<Creative>(pageResponse.getTotalElements(), searchRequest.getDraw(), pageResponse.getTotalElements(), pageResponse.getContent());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
	}
	@Loggable
	@Transactional
	public List<Creative> getCreativesByName(String name) throws OMSSystemException {
		List<Creative> creatives=creativeRepository.findCreativeByName(name, false);
		if(creatives!=null){
			return creatives;
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
				"Creative with name " + '"' + name + '"' + " does not exist.");
	}
	@Loggable
	@Transactional
	public Creative getCreativeById(Long creativeID) throws OMSSystemException {
		if(creativeRepository.findById(creativeID)!=null){
			return creativeRepository.findById(creativeID);
			}
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Creative with {" + creativeID + "} does not exist.");
		
	}

	@Transactional
	public Creative addCreative(Creative creative) throws OMSSystemException{
		try{
			Creative existingCreative = creativeRepository.findCreativeByNameCaseInsensitive(creative.getName().toLowerCase(), false);
			if (null != existingCreative) {
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
						"Creative with name " + '"' + creative.getName() + '"' + " already exist");
			}
			return creativeRepository.save(creative);
		}catch(Exception e){
			e.printStackTrace();
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Transactional
	@Override
	public void deleteCreative(Long creativeId) throws OMSSystemException {
		Creative creative = creativeRepository.findById(creativeId);
		if(creative != null && Boolean.FALSE.equals(creative.isDeleted())){
			creativeRepository.softDelete(creativeId);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Creative with {" + creativeId + "} does not exist or already deleted.");
		}
	}

	@Transactional
	public Creative updateCreative(Creative creative) throws OMSSystemException {
		Creative creativeDB = getCreativeById(creative.getCreativeId());
		try {
			if (creativeDB == null
					|| Boolean.FALSE.equals(creativeDB.isDeleted())) {
				throw new OMSSystemException(Status.FAILED.name(),
						HttpStatus.CONFLICT, "Creative with {"
								+ creative.getCreativeId()
								+ "} does not exist.");

			}
			creativeRepository.save(creative);
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(),
					HttpStatus.CONFLICT, "unable to update creative id : "
							+ creative.getCreativeId());
		}
		return creative;
	}
	private static Pageable getPagination(SearchRequestVO searchRequest){
		List<Columns> columns = searchRequest.getColumns();
		List<SearchOrderVO> orders = searchRequest.getOrder();
		List<Order> orderList = new ArrayList<>();
		
		orders.forEach(item->{
			int columnIndex = item.getColumn();
			String orderedByColumn = columns.get(columnIndex).getData();
			Direction dir = "asc".equals(item.getDir())? Direction.ASC : Direction.DESC;
			orderList.add(new Order(dir, orderedByColumn));
		});
		if(orderList.size()==0){
			orderList.add(new Order(Direction.DESC, "updated"));
		}
		int pageNumber = searchRequest.getStart()/searchRequest.getLength();
		PageRequest request = new PageRequest(pageNumber, searchRequest.getLength(),new Sort(orderList));
		return request;
	}

}
