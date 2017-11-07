package com.oms.service;

import java.util.List;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Product;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

public interface ProductService {
	public Product addProduct(Product p);
	public Product updateProduct(Product p)throws OMSSystemException;
	public List<Product> listProducts();
	public Product getProductById(Long id) throws OMSSystemException ;
	public void removeProduct(Long productId) throws OMSSystemException;
	public PaginationResponseVO<Product> searchProduct(SearchRequestVO searchRequest);
	public String[] getDeliveryMasterData(String type);
}
