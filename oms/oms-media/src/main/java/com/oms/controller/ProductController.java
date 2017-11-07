package com.oms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

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
import com.oms.model.Product;
import com.oms.service.ProductService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@RestController
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends BaseController {

	final static Logger logger = Logger.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;


	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public List<Product> listProducts() {
		List<Product> products = productService.listProducts();
		logger.info("List of Products " + products.toString());
		return products;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Product getProductById(@PathVariable Long id)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		return productService.getProductById(id);
	}

	// For add the product
	@RequestMapping(method = RequestMethod.POST)
	@Auditable(actionType = AuditingActionType.ADD_NEW_PRODUCT)
	public Product addProduct(@Valid @RequestBody Product productDTO) throws OMSSystemException {
		productDTO.setCreatedBy(findUserIDFromSecurityContext());
		productDTO.setUpdatedBy(findUserIDFromSecurityContext());
		return productService.addProduct(productDTO);
	}

	@RequestMapping(value = "/{productId}",method = RequestMethod.DELETE)
	@Auditable(actionType = AuditingActionType.DELETE_PRODUCT)
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void removeProduct(@PathVariable("productId") Long productId)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		productService.removeProduct(productId);
	}

	@RequestMapping(method = RequestMethod.PUT)
	@Auditable(actionType = AuditingActionType.UPDATE_PRODUCT)
	public Product updateProduct(@RequestBody Product productDTO)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		productDTO.setUpdatedBy(findUserIDFromSecurityContext());
		productDTO.setUpdated(new Date());
		return productService.updateProduct(productDTO);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public PaginationResponseVO<Product> searchProduct(@RequestBody SearchRequestVO searchRequest ){
		return productService.searchProduct(searchRequest);
	}
	
	@RequestMapping(value = "/delivery/metadata/{type}",method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public String[] getDeliveryMasterData(@PathVariable String type) {
		return productService.getDeliveryMasterData(type);
	}

}
