package com.brijframework.authorization.view.model.onboadring;

import com.brijframework.authorization.constant.BillingType;
import com.brijframework.authorization.constant.ServiceType;
import com.brijframework.authorization.model.UIModel;

public class UIViewOnBoardingBilling extends UIModel {

	private String idenNo;
	
	private BillingType type;
	
	private ServiceType service;
	
	private Double amount;
	
	private Double discount;

	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
	}

	public BillingType getType() {
		return type;
	}

	public void setType(BillingType type) {
		this.type = type;
	}

	public ServiceType getService() {
		return service;
	}

	public void setService(ServiceType service) {
		this.service = service;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}
