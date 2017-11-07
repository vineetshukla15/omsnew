package com.oms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Creative;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public interface CreativeService {
	public List<Creative> getAllCreatives();
	public List<Creative> getCreativesByName(String name) throws OMSSystemException;
	public Creative getCreativeById(Long id) throws OMSSystemException;
	public Creative addCreative(Creative creative);
	public void deleteCreative(Long creativeId) throws OMSSystemException;
	public Creative updateCreative(Creative creative) throws OMSSystemException;
	public PaginationResponseVO<Creative> searchCreatives(SearchRequestVO searchRequest);
}
