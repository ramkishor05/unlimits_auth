package com.brijframework.rest.crud.beans;

import java.util.List;

public class PageDetail {
	
	private long totalElements;
	
	private List<?> data;

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

}
