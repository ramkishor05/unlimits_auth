package com.brijframework.authorization.view.entities.onboarding;
import static com.brijframework.authorization.view.constants.ViewTableConstants.VIEW_ONBOARDING_BILLING;

import com.brijframework.authorization.constant.BillingType;
import com.brijframework.authorization.constant.PaymentMode;
import com.brijframework.authorization.entities.EOPayment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = VIEW_ONBOARDING_BILLING, uniqueConstraints = { 
		@UniqueConstraint(columnNames = { "TYPE","SERVICE" }) })
public class EOViewOnBoardingBilling extends EOPayment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "IDEN_NO")
	private String idenNo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE")
	private BillingType type;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "SERVICE")
	private PaymentMode service;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "DISCOUNT")
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

	public PaymentMode getService() {
		return service;
	}

	public void setService(PaymentMode service) {
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

	@Override
	public String toString() {
		return "EOViewOnBoardingBilling [type=" + type + ", service=" + service + ", amount=" + amount + ", discount="
				+ discount + "]";
	}

	
}
