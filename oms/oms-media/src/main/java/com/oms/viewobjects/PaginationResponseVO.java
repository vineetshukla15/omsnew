package com.oms.viewobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PaginationResponseVO<E> {
	private Long recordsTotal;
	private Integer draw;
	private Long recordsFiltered;
	private Collection<E> data = new ArrayList<>();

	public PaginationResponseVO() {
		super();
	}

	public PaginationResponseVO(Long recordsTotal, Integer draw, Long recordsFiltered, List<E> data) {
		super();
		this.recordsTotal = recordsTotal;
		this.draw = draw;
		this.recordsFiltered = recordsFiltered;
		this.data = data;
	}

	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public Collection<E> getData() {
		return data;
	}

	public void setData(Collection<E> data) {
		this.data = data;
	}

}
