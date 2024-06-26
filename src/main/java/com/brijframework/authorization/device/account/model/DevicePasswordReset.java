package com.brijframework.authorization.device.account.model;

import com.brijframework.authorization.constant.Authority;
import com.brijframework.authorization.constant.ResetBy;

public class DevicePasswordReset extends DeviceLoginRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Authority authority;
	
	private Integer otp;
	
	private ResetBy resetBy; 

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public ResetBy getResetBy() {
		return resetBy;
	}

	public void setResetBy(ResetBy resetBy) {
		this.resetBy = resetBy;
	}
}
