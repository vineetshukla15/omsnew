package com.oms.viewobjects;

public class DataTablePageAttributeVO {
	private int draw;
	private int recordsTotal;
	private int recordsFiltered;
	
	public DataTablePageAttributeVO() {
	
	}

	public DataTablePageAttributeVO(int draw, int recordsTotal, int recordsFiltered) {
		this.draw = draw;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	@Override
	public String toString() {
		return "DataTablePageAttributeVO [draw=" + draw + ", recordsTotal=" + recordsTotal + ", recordsFiltered="
				+ recordsFiltered + "]";
	}
	
}
