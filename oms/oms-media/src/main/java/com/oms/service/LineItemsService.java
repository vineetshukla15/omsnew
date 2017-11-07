package com.oms.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.LineItems;
import com.oms.viewobjects.LineItemListVO;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@Service
public interface LineItemsService {
	public LineItems addLineItem(LineItems lineItems);
	public LineItems updateLineItems(LineItems lineItems)throws OMSSystemException;
    public List<LineItems> getAllLineItems();
	public LineItems getLineItemById(Long lineItemId) throws OMSSystemException;
	public void removeLineItems(Long lineItemId)throws OMSSystemException;
	public PaginationResponseVO<LineItemListVO> searchLineItems(SearchRequestVO searchRequest);
	public BigDecimal getPrice(LineItems lineItem) throws OMSSystemException;

}
