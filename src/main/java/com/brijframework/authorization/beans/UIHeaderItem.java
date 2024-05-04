package com.brijframework.authorization.beans;

public class UIHeaderItem {

	private long id;
	private String idenNo;
	private String title;
	private String url;
	private String icon;
	private String type;
	private Double orderSequence;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Double getOrderSequence() {
		if(orderSequence==null) {
			orderSequence=0d;
		}
		return orderSequence;
	}

	public void setOrderSequence(Double orderSequence) {
		this.orderSequence = orderSequence;
	}

}
