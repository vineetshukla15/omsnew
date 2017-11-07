package com.oms.viewobjects;

import org.springframework.data.domain.Page;

public class PageAttributesVO {
	private Boolean isLastPage;
	private Boolean isfirstPage;
	private Long totalElements;
	private Integer totalPages;
	private Integer noOfCurrentPageElements;
	private Integer pageSize;
	
	

	public PageAttributesVO(Page<?> page) {
		super();
		this.isLastPage = page.isLast();
		this.isfirstPage = page.isFirst();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.noOfCurrentPageElements = page.getNumberOfElements();
		this.pageSize = page.getSize();
	}

	public Boolean getIsLastPage() {
		return isLastPage;
	}

	public void setIsLastPage(Boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public Boolean getIsfirstPage() {
		return isfirstPage;
	}

	public void setIsfirstPage(Boolean isfirstPage) {
		this.isfirstPage = isfirstPage;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getNoOfCurrentPageElements() {
		return noOfCurrentPageElements;
	}

	public void setNoOfCurrentPageElements(Integer noOfCurrentPageElements) {
		this.noOfCurrentPageElements = noOfCurrentPageElements;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
