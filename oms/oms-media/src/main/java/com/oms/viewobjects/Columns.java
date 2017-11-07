package com.oms.viewobjects;

public class Columns {
	private String data;

	private String name;

	private boolean searchable;

	private boolean orderable;

	private Search search;

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return this.data;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public boolean getSearchable() {
		return this.searchable;
	}

	public void setOrderable(boolean orderable) {
		this.orderable = orderable;
	}

	public boolean getOrderable() {
		return this.orderable;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public Search getSearch() {
		return this.search;
	}
}
