package com.oms.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.ProductType;
import com.oms.repository.ProductTypeRepository;
import com.oms.service.ProductTypeService;

@Service
public class ProductTypeServiceImpl implements ProductTypeService{
	
	final static Logger LOGGER = Logger.getLogger(ProductTypeServiceImpl.class);
	
	@Autowired
	private ProductTypeRepository productTypeRepository;

	@Override
	@Transactional
	public List<ProductType> getAllProductType() throws IllegalAccessException, InvocationTargetException {
		List<ProductType> producttypes = null;
		try {
			producttypes = productTypeRepository.findAll();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive ProductType information",ex);
		}
		return producttypes;
	}

	@Override
	@Transactional
	public ProductType getProductType(Long id)
			throws OMSSystemException, IllegalAccessException, InvocationTargetException {
		if (productTypeRepository.findById(id) != null) {
			return productTypeRepository.findById(id);
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"Product Type with is {" + id + "} does not exist.");

	}

	@Override
	@Transactional
	public ProductType addProductType(ProductType productTypeDTO)
			throws IllegalAccessException, InvocationTargetException, Exception {
		return productTypeRepository.save(productTypeDTO);
	}

	@Override
	@Transactional
	public ProductType updateProductType(ProductType productTypeDTO)
			throws IllegalAccessException, InvocationTargetException, Exception, OMSSystemException {
		ProductType productTypeDB = getProductType(productTypeDTO.getTypeId());
		if(productTypeDB!=null && Boolean.FALSE.equals(productTypeDB.isDeleted())){
			try{
				productTypeDTO.setCreatedBy(productTypeDB.getCreatedBy());
				productTypeDTO.setCreated(productTypeDB.getCreated());
				productTypeRepository.save(productTypeDTO);
				return productTypeDTO;
			}catch(Exception ex){
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
						"Unable to update product type id : " + productTypeDTO.getTypeId());
			}
		}throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"Product Type with is {" + productTypeDTO.getTypeId() + "} does not exist.");
		
	}

	@Override
	@Transactional
	public void deleteProductType(Long typeId) throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		ProductType productType = productTypeRepository.findById(typeId);
		if (productType != null && Boolean.FALSE.equals(productType.isDeleted())) {
			productTypeRepository.softDelete(typeId);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Product Type with Id {" + typeId + "} does not exist or already deleted.");
		}
	}

}
