package com.brijframwork.authorization.beans;

public class UIMenuItem {

	private long id;
	private String idenNo;
	private String title;
	private String url;
	private String icon;
	private String type;
	private Double orderSequnce;
	private boolean homePage;
	private Boolean onBoarding;
	private Boolean disabled;
	
	public Boolean getOnBoarding() {
		if(onBoarding==null) {
			return false;
		}
		return onBoarding;
	}

	public void setOnBoarding(Boolean onBoarding) {
		this.onBoarding = onBoarding;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	

	public Double getOrderSequnce() {
		if(orderSequnce==null) {
			orderSequnce=0d;
		}
		return orderSequnce;
	}

	public void setOrderSequnce(Double orderSequnce) {
		this.orderSequnce = orderSequnce;
	}

	public boolean isHomePage() {
		return homePage;
	}

	public void setHomePage(boolean homePage) {
		this.homePage = homePage;
	}

	public Boolean getDisabled() {
		if(disabled==null) {
			return false;
		}
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public String toString() {
		return "UIMenuItem [id=" + id + ", idenNo=" + idenNo + ", title=" + title + ", url=" + url + ", icon=" + icon
				+ ", type=" + type + ", orderSequnce=" + orderSequnce + ", homePage=" + homePage + ", onBoarding=" + onBoarding
				+ ", disabled=" + disabled + "]";
	}
	
}
