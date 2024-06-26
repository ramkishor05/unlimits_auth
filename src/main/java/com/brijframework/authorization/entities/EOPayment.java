package com.brijframework.authorization.entities;

import java.io.Serializable;

import com.brijframework.authorization.constant.BillingType;
import com.brijframework.authorization.constant.ServiceType;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EOPayment extends EOEntityObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BillingType type;
	private ServiceType service;
	private Double amount;
	private Double discount;

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
