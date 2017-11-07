package com.oms.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.ProductType;

public interface ProductTypeService {
	
public List<ProductType> getAllProductType() throws IllegalAccessException, InvocationTargetException;
	
	public ProductType getProductType(Long id) throws OMSSystemException, IllegalAccessException, InvocationTargetException;

	public ProductType addProductType(ProductType productTypeDTO) throws IllegalAccessException, InvocationTargetException, Exception;

	public ProductType updateProductType(ProductType productTypeDTO) throws IllegalAccessException, InvocationTargetException, Exception;

	public void deleteProductType(Long typeId) throws IllegalAccessException, InvocationTargetException, OMSSystemException;

}
