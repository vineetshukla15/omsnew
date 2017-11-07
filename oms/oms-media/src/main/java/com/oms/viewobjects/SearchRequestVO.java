package com.oms.viewobjects;

import java.util.List;

public class SearchRequestVO {
	private int draw;

	private List<Columns> columns;
	
	private List<Filters> filters;

	private List<SearchOrderVO> order;

	private int start;

	private int length;

	private GlobalSearch search;

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getDraw() {
		return this.draw;
	}

	public void setColumns(List<Columns> columns) {
		this.columns = columns;
	}

	public List<Columns> getColumns() {
		return this.columns;
	}

	public List<Filters> getFilters() {
		return filters;
	}

	public void setFilters(List<Filters> filters) {
		this.filters = filters;
	}

	public void setOrder(List<SearchOrderVO> order) {
		this.order = order;
	}

	public List<SearchOrderVO> getOrder() {
		return this.order;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStart() {
		return this.start;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getLength() {
		return this.length;
	}

	public void setSearch(GlobalSearch search) {
		this.search = search;
	}

	public GlobalSearch getSearch() {
		return this.search;
	}
}
