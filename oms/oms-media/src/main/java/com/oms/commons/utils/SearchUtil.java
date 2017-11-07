package com.oms.commons.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.oms.viewobjects.Columns;
import com.oms.viewobjects.SearchOrderVO;
import com.oms.viewobjects.SearchRequestVO;

public class SearchUtil {
	public static Pageable getPageable(SearchRequestVO searchRequest){
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
