package com.oms.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.commons.utils.SearchUtil;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.Product;
import com.oms.model.RateCard;
import com.oms.model.specification.ProductSpecification;
import com.oms.repository.ProductRepository;
import com.oms.service.ProductService;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public class ProductServiceImpl implements ProductService {

	final static Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;
	
    @Value("${priority}")
	private String priority;
    
    @Value("${delivery_impressions}")
	private String delivery_impressions;
    
    @Value("${display_creatives}")
	private String display_creatives;
    
    @Value("${rotate_creatives}")
	private String rotate_creatives;

	@Transactional
	@Override
	public List<Product> listProducts() {
		List<Product> productList = null;
		try {
			productList = productRepository.findByDeletedFalseOrderByName();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive Product information",ex);
		}
		return productList;

	}

	@Transactional
	@Override
	public Product getProductById(Long id) throws OMSSystemException {
		if (productRepository.findById(id) != null) {
			return productRepository.findById(id);
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
				"Product with is {" + id + "} does not exist.");
	}

	@Transactional
	@Override
	public void removeProduct(Long productId) throws OMSSystemException {
		Product product = productRepository.findById(productId);
		if (product != null && Boolean.FALSE.equals(product.isDeleted())) {
			productRepository.softDelete(productId);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Product with Id {" + productId + "} does not exists or already deleted.");
		}
	}

	@Transactional
	@Override
	public Product addProduct(Product product) throws OMSSystemException {
		
		Product productByName =  productRepository.findByName(product.getName().toLowerCase(), false);
		if(productByName != null){
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Product with name " + product.getName() + " already exist");
		}
		try {
			return productRepository.save(product);
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, ex.getMessage(),ex);
		}

	}

	@Transactional
	@Override
	public Product updateProduct(Product productDTO) throws OMSSystemException {
		try {
			Product productByName =  productRepository.findByName(productDTO.getName().toLowerCase(), false);
			if(null != productByName && productDTO.getName().equals(productByName.getName()) && !productDTO.getProductId().equals(productByName.getProductId())){
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
						"Product with name " + productDTO.getName() + " already exist");
			}
			if (productDTO.getProductId() != null && Boolean.FALSE.equals(productDTO.isDeleted())) {
				RateCard ratecard=productDTO.getRateCard();
				productDTO.setCreated(productByName.getCreated());
				productDTO.setCreatedBy(productByName.getCreatedBy());
				productDTO.setRateCard(null);
				System.out.println(ratecard);
				return productRepository.save(productDTO);
			}
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND,
					"Product with id {" + productDTO.getProductId() + "} does not exist.");
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@Override
	public PaginationResponseVO<Product> searchProduct(SearchRequestVO searchRequest) {
		Page<Product> pageResponse = null;
		PaginationResponseVO<Product> response = null;
		try {
			pageResponse = productRepository.findAll(ProductSpecification.getProductSpecification(searchRequest),
					SearchUtil.getPageable(searchRequest));
			response = new PaginationResponseVO<Product>(pageResponse.getTotalElements(), searchRequest.getDraw(),
					pageResponse.getTotalElements(), pageResponse.getContent());
		} catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.NOT_FOUND, "No Data Found");
		}

		System.out.println("");
		return response;
	}
@Override
	public String[] getDeliveryMasterData(String type){
		String[] result = null;
		type=type.toLowerCase();
		switch(type){
		case "priority":{
			result=priority.split(";");
			break;
		}
		case "delivery_impressions":{
			result=delivery_impressions.split(";");
			break;
		}
		case "display_creatives":{
			result=display_creatives.split(";");
			break;
		}
		case "rotate_creatives":{
			result=rotate_creatives.split(";");
			break;
		}
		default:{
			throw new OMSSystemException(Status.FAILED.name(),HttpStatus.CONFLICT,"Provided type "+type+" do not found ");
		}
		}
		return result;
	}
}
