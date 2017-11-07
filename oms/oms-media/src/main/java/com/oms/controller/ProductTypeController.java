package com.oms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

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
import com.oms.model.ProductType;
import com.oms.service.ProductTypeService;


@RestController
@RequestMapping(value = "/productType",produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductTypeController extends BaseController{

		@Autowired
		private ProductTypeService productTypeService;

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public List<ProductType> getAllProductType() throws IllegalAccessException, InvocationTargetException {
			return productTypeService.getAllProductType();
		}
		
		@RequestMapping(value = "/{productTypeId}", method = RequestMethod.GET)
		public ProductType getProductType(@PathVariable Long productTypeId) throws IllegalAccessException, InvocationTargetException, OMSSystemException {
			return productTypeService.getProductType(productTypeId);
		}

		@RequestMapping( method = RequestMethod.POST)
		@Auditable(actionType = AuditingActionType.ADD_PRODUCT_TYPE)
		public  ProductType addNewProductType(@RequestBody ProductType productTypeDTO) throws IllegalAccessException, InvocationTargetException, Exception {
			productTypeDTO.setCreatedBy(findUserIDFromSecurityContext());
			productTypeDTO.setCreated(new Date());
			productTypeDTO.setUpdatedBy(findUserIDFromSecurityContext());
			productTypeDTO.setUpdated(new Date());
			return productTypeService.addProductType(productTypeDTO);
		}

		@RequestMapping( method = RequestMethod.PUT)
		@Auditable(actionType = AuditingActionType.UPDATE_PRODUCT_TYPE)
		public  ProductType updateProductType(@RequestBody ProductType productTypeDTO) throws IllegalAccessException, InvocationTargetException, Exception {
			productTypeDTO.setUpdatedBy(findUserIDFromSecurityContext());
			productTypeDTO.setUpdated(new Date());
			return productTypeService.updateProductType(productTypeDTO);
		}

		@RequestMapping( value="/{typeId}", method = RequestMethod.DELETE)
		@Auditable(actionType = AuditingActionType.DELETE_PRODUCT_TYPE)
		@ResponseStatus(code = HttpStatus.NO_CONTENT)
		public void deleteProductType(@PathVariable("typeId") Long typeId) throws IllegalAccessException, InvocationTargetException, OMSSystemException {
			productTypeService.deleteProductType(typeId);
		}

	}


