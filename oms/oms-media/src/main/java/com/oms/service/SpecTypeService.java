package com.oms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.model.SpecType;

@Service
public interface SpecTypeService {
	
	public List<SpecType> listSpecType();

}
